/**
 * Copyright © 2017 Heinz&Knurre (andreas.gorbach@gmail.com christian.d.middel@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.heinz.und.knurre.blackoutpedia;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Main class for the kraken sub command.
 */
public class MainKraken {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainKraken.class);

    public static void exec(KrakenCommand cmd) {

        LOGGER.info("executing kraken sub command");
        LOGGER.info("attaching media database at store={}", cmd.getStore());

        // Check the arguments in more detail
        if (!Files.exists(cmd.getStore()) || !Files.isDirectory(cmd.getStore())) {
            LOGGER.error("storage directory={} does not exist or is not a directory", cmd.getStore());
            System.exit(ExitCodes.ARGUMENTS.code());
        }
        if (!Files.isWritable(cmd.getStore())) {
            LOGGER.error("storage directory={} is not writable", cmd.getStore());
            System.exit(ExitCodes.ARGUMENTS.code());
        }
        if (cmd.getChannels() < 1 || cmd.getChannels() > 200) {
            LOGGER.error("invalid number of channels={} specified, must be >= 1 and <= 200", cmd.getChannels());
            System.exit(ExitCodes.ARGUMENTS.code());
        }
        if (cmd.getMax() < 1) {
            LOGGER.error("invalid maximum number of files to be downloaded={} specified, must be >= 1", cmd.getMax());
            System.exit(ExitCodes.ARGUMENTS.code());
        }

        Path dbPath = Paths.get(cmd.getStore().toString(), "media.map");
        DB mapDB = DBMaker
                .fileDB(dbPath.toFile())
                .fileMmapEnable()
                .make();
        ConcurrentMap<String, String> mapFile = mapDB
                .hashMap("file", Serializer.STRING, Serializer.STRING)
                .createOrOpen();
        ConcurrentMap<String, Boolean> mapLoaded = mapDB
                .hashMap("loaded", Serializer.STRING, Serializer.BOOLEAN)
                .createOrOpen();
        ConcurrentMap<String, Integer> mapFault = mapDB
                .hashMap("fault", Serializer.STRING, Serializer.INTEGER)
                .createOrOpen();

        // We add a shutdown hook for the media database
        LOGGER.info("attaching shutdown hook for media database");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            mapDB.close();
        }));

        // First we make sure the media database is initialized
        if (mapFile.size() > 0) {
            LOGGER.info("media database at store={} is already initialized", cmd.getStore());
        } else {

            LOGGER.info("attaching index at store={}", cmd.getStore());
            Index index;
            try {
                index = new Index(cmd.getStore());
            } catch (IOException e) {
                LOGGER.error("index initialization failed for store={}", cmd.getStore(), e);
                System.exit(ExitCodes.INDEX.code());
                throw new RuntimeException(e);
            }

            // We add a shutdown hook to properly shutdown the index
            // when the JVM shuts down (to prevent index damage)
            LOGGER.info("attaching shutdown hook for index");
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    index.shutdown();
                } catch (IOException e) {
                    LOGGER.error("index shutdown failed for store={}, index might be corrupted", cmd.getStore(), e);
                }
            }));

            Pattern p;
            switch (cmd.getLanguage()) {
                case DE:
                    p = Pattern.compile("\\[\\[Datei:([^|\\]]*)");
                    break;
                case FR:
                    p = Pattern.compile("\\[\\[Fichier:([^|\\]]*)");
                    break;
                default:
                    p = Pattern.compile("\\[\\[File:([^|\\]]*)");
                    break;
            }
            LOGGER.info("kraken starts initializing media database");

            // Run through all wikipedia pages
            Long start = System.currentTimeMillis();
            Long lastProgress = 0L;
            Set<String> hashes = new HashSet<>();
            for (int i = 0; i < index.getPageCount(); i++) {

                WikipediaPage page = index.get(i);

                // Find all occurrences of file references
                Matcher m = p.matcher(page.getText());
                while (m.find()) {

                    String name = m.group(1);

                    // We skip .ogg audio files
                    if (name.toLowerCase().endsWith(".ogg")) {
                        continue;
                    }
                    // We skip macro files
                    if (name.contains("{") || name.contains("}")) {
                        continue;
                    }

                    String hash = DigestUtils.md5Hex(name);

                    // We skip if the name is already known
                    if (hashes.contains(hash) || mapFile.containsKey(hash)) {
                        LOGGER.debug("skipping already existing hash {}", hash);
                        continue;
                    }

                    mapFile.putIfAbsent(hash, name);
                    hashes.add(hash);
                }

                Long progress = (Long.valueOf(i) * 100) / index.getPageCount();
                if (lastProgress < progress) {
                    Long duration = System.currentTimeMillis() - start;
                    Long eta = ((index.getPageCount() - Long.valueOf(i)) * duration) / Long.valueOf(i);
                    LOGGER.info("kraken media database initialization progress={}%, eta(min)={}", progress, (eta / 60000));
                    lastProgress = progress;
                }
            }

            // We make sure the media database is committed
            LOGGER.info("synchronizing media database at store={}", cmd.getStore());
            mapDB.commit();

            LOGGER.info("media database initialized with {} file(s)", hashes.size());

            LOGGER.info("shutting down index at store={}", cmd.getStore());
            try {
                index.shutdown();
            } catch (IOException e) {
                LOGGER.error("index shutdown failed for store={}, index might be corrupted", cmd.getStore(), e);
            }
        }

        // Now we make sure we have a temporary file per channel
        LinkedBlockingQueue<Path> channels = new LinkedBlockingQueue<>();
        for (int i = 0; i < cmd.getChannels(); i++) {
            try {
                channels.add(Files.createTempFile("blackoutpedia", ".channel"));
            } catch (IOException e) {
                LOGGER.error("failed to create temporary file for download channel", e);
                LOGGER.info("closing media database at store={}", cmd.getStore());
                mapDB.close();
                System.exit(ExitCodes.CHANNELS.code());
            }
        }

        // Now initialize a thread pool for the channels
        ExecutorService executor = Executors.newFixedThreadPool(cmd.getChannels());

        // Then we extract files to be loaded
        LOGGER.info("kraken determines files to be downloaded");
        List<String> hashes = new ArrayList<>();
        Long load = 0L;
        for (String hash : mapFile.keySet()) {
            if (!mapLoaded.containsKey(hash) && !mapFault.containsKey(hash)) {
                hashes.add(hash);
                load++;
            }
            if (load >= cmd.getMax()) {
                break;
            }
        }

        LOGGER.info("kraken starts downloading {} file(s)", hashes.size());
        URI commons = null;
        try {
            commons = new URIBuilder("https://commons.wikimedia.org/w/index.php").addParameter("title", "Special:FilePath").build();
        } catch (URISyntaxException e) {
            LOGGER.error("base URI cannot be initialized", e);
        }
        RequestConfig requestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD).build();
        CloseableHttpClient http = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();
        Long counter = 0L;
        float lastProgress = 0f;
        Long start = System.currentTimeMillis();
        for (String hash : hashes) {

            counter++;

            float progress = (float) ((counter * 10000) / load) / 100;
            if (lastProgress < progress) {
                Long duration = System.currentTimeMillis() - start;
                Long eta = ((load - counter) * duration) / counter;
                LOGGER.info("kraken downloading progress={}%, eta(min)={}", progress, (eta / 60000));
                lastProgress = progress;
            }

            String name = mapFile.get(hash);
            try {
                URI uri = new URIBuilder(commons).addParameter("file", name).build();
                Path channel = channels.take();
                HttpGet request = new HttpGet(uri);
                executor.submit(() -> {
                    try {
                        http.execute(
                                request,
                                new DownloadHandler(
                                        hash,
                                        name,
                                        channel,
                                        mapLoaded,
                                        mapFault,
                                        cmd.getStore(),
                                        channels
                                )
                        );
                    } catch (IOException e) {
                        LOGGER.error("downloading execution failed for file={}", name, e);
                        try {
                            channels.put(channel);
                        } catch (InterruptedException ex) {
                            LOGGER.error("returning slot interrupted", e);
                        }
                    }
                });
            } catch (IllegalArgumentException | URISyntaxException e) {
                LOGGER.error("invalid url for file={}", name, e);
            } catch (InterruptedException e) {
                LOGGER.error("waiting for slot interrupted", e);
            }
        }

        LOGGER.info("kraken has finished downloading {} file(s)", hashes.size());

        LOGGER.info("shutting down channels");
        executor.shutdown();

        LOGGER.info("committing media database at store={}", cmd.getStore());
        mapDB.commit();

        LOGGER.info("media database overall fault file count={}", mapFault.size());
        LOGGER.info("overall fault rate={}%", ((mapFault.size() * 100) / mapFile.size()));
        LOGGER.info("media database overall downloaded file count={}", mapLoaded.size());
        LOGGER.info("overall downloaded rate={}%", ((mapLoaded.size() * 100) / (mapFile.size() - mapFault.size())));

        LOGGER.info("closing media database at store={}", cmd.getStore());
        mapDB.close();
    }
}
