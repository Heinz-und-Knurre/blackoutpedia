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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Main class for the index sub command.
 */
public class MainIndex {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainIndex.class);

    public static void exec(IndexCommand cmd) {

        LOGGER.info("executing index sub command");
        LOGGER.info("attaching to index at store={}", cmd.getStore());

        // Check the arguments in more detail
        if (!Files.exists(cmd.getStore()) || !Files.isDirectory(cmd.getStore())) {
            LOGGER.error("storage directory={} does not exist or is not a directory", cmd.getStore());
            System.exit(ExitCodes.ARGUMENTS.code());
        }
        if (!Files.isWritable(cmd.getStore())) {
            LOGGER.error("storage directory={} is not writable", cmd.getStore());
            System.exit(ExitCodes.ARGUMENTS.code());
        }
        if (!Files.exists(cmd.getDump())) {
            LOGGER.error("wikipedia dump archive={} does not exist", cmd.getDump());
            System.exit(ExitCodes.ARGUMENTS.code());
        }
        if (!Files.isReadable(cmd.getDump())) {
            LOGGER.error("wikipedia dump archive={} is not readable", cmd.getDump());
            System.exit(ExitCodes.ARGUMENTS.code());
        }

        // We initialize the index page cmd.getStore()
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

        // Parse if no pages are in the index and a dump is given
        if (cmd.getDump() != null) {

            // We check if the index directory partition has at least 4 times the dump file size
            Long dumpSize;
            try {
                dumpSize = Files.size(cmd.getDump());
                LOGGER.info("dump size(mb)={}", (dumpSize / 1024 / 1024));
            } catch (IOException e) {
                LOGGER.error("cannot determine size of dump={}", cmd.getDump(), e);
                throw new RuntimeException(e);
            }
            Long usable = cmd.getStore().toFile().getUsableSpace();
            LOGGER.info("usable disk space at store={}", (usable / 1024 / 1024));
            if (usable < (dumpSize * 4)) {
                LOGGER.error("not enough disk space at store={}, at least 4 times the dump size required", cmd.getStore());
                System.exit(ExitCodes.DISKSPACE.code());
                throw new RuntimeException();
            }

            if (index.getPageCount() > 0) {
                LOGGER.info("index at store={} already contains documents, wikipedia dump will not be processed", cmd.getStore());
            } else {
                Parser parser;
                parser = new Parser(cmd.getDump(), index);
                try {
                    parser.parse();
                } catch (IOException | ParserConfigurationException | SAXException | PageStoreAddException e) {
                    LOGGER.error("failed to parse dump={}", cmd.getDump(), e);
                    System.exit(ExitCodes.PARSE.code());
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
