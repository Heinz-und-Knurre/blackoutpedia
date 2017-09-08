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

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Implements a download handler for the apache http client. The file is first serialized
 * to a temporary location. If that succeeds the file is moved into its final destination.
 * Finally the file is recorded to be downloaded in the persistent map.
 */
public class DownloadHandler implements ResponseHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(DownloadHandler.class);

    private final String hash;
    private final String name;
    private final Path destination;
    private final ConcurrentMap<String, Boolean> mapLoaded;
    private final ConcurrentMap<String, Integer> mapFault;
    private final Path store;
    private final LinkedBlockingQueue<Path> channels;

    public DownloadHandler(
            String hash, String name, Path destination, ConcurrentMap<String, Boolean> mapLoaded,
            ConcurrentMap<String, Integer> mapFault, Path store, LinkedBlockingQueue<Path> channels) {

        this.hash = hash;
        this.name = name;
        this.destination = destination;
        this.mapLoaded = mapLoaded;
        this.mapFault = mapFault;
        this.store = store;
        this.channels = channels;
    }

    @Override
    public Object handleResponse(HttpResponse response) throws IOException {

        // We only proceed when getting HTTP 200
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

            try (final BufferedOutputStream os = new BufferedOutputStream(
                    Files.newOutputStream(this.destination, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)
            )) {

                response.getEntity().writeTo(os);

                String f1 = String.valueOf(this.hash.charAt(0));
                String f2 = String.valueOf(this.hash.charAt(1));
                String f3 = String.valueOf(this.hash.charAt(2));
                String f4 = String.valueOf(this.hash.charAt(4));

                Path basePath = Paths.get(store.toString(), "media");
                Files.createDirectories(Paths.get(basePath.toString(), f1, f2, f3, f4));
                Path target = Paths.get(basePath.toString(), f1, f2, f3, f4, this.name);

                try {
                    Files.move(this.destination, target);
                } catch (FileAlreadyExistsException e) {
                    LOGGER.info("already downloaded file={}, correcting media database", this.name);
                }

                mapLoaded.putIfAbsent(hash, Boolean.TRUE);

            } catch (IOException e) {
                LOGGER.error("download failed when streaming file={}", this.name, e);
            }
        } else {
            LOGGER.error("file={} cannot be downloaded, HTTP status={}", this.name, response.getStatusLine().getStatusCode());
            mapFault.putIfAbsent(this.hash, response.getStatusLine().getStatusCode());
        }

        // Here we return slot after success or failure of the download
        try {
            this.channels.put(this.destination);
        } catch (InterruptedException ex) {
            LOGGER.error("returning slot was interrupted", ex);
        }
        return null;
    }
}
