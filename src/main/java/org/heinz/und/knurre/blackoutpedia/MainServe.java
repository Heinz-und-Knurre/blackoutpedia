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

import io.undertow.Handlers;
import io.undertow.Undertow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;

/**
 * Main class for the serve sub command.
 */
public class MainServe {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainServe.class);

    public static void exec(ServeCommand cmd) {

        LOGGER.info("executing serve sub command");
        LOGGER.info("attaching to index at store={}", cmd.getStore());

        // Check the arguments in more detail
        if (!Files.exists(cmd.getStore()) || !Files.isDirectory(cmd.getStore())) {
            LOGGER.error("storage directory={} does not exist or is not a directory", cmd.getStore());
            System.exit(ExitCodes.ARGUMENTS.code());
        }

        // We initialize the index page store
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

        // We bring up the HTTP server
        LOGGER.info("starting HTTP server at port={}", cmd.getPort());
        LOGGER.info("binding HTTP server to address={}", cmd.getBind());
        Undertow server = Undertow.builder()
                .addHttpListener(cmd.getPort(), cmd.getBind())
                .setHandler(Handlers.path()
                        .addExactPath("/", new HttpGetHandler(index))
                        .addExactPath("/css", new CssHandler())
                )
                .build();
        server.start();
    }
}
