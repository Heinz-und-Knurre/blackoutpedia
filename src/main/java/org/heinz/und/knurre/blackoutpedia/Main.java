/**
 * Copyright © 2017 Heinz&Knurre (andreas.gorbach@gmail.com christian.d.middel@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.heinz.und.knurre.blackoutpedia;

import io.undertow.Handlers;
import io.undertow.Undertow;
import org.apache.commons.io.IOUtils;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

/**
 * Implements the main class that organizes command line parsing
 * and initializes the system accordingly.
 */
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    /**
     * @param args Command line arguments
     */
    public static void main(String[] args) {

        // Print version information
        try {
            List<String> banner = IOUtils.readLines(Main.class.getResourceAsStream("/banner.txt"));
            for (String line : banner) {
                LOGGER.info(line);
            }
        } catch (IOException e) {
            LOGGER.info("BlackoutPedia");
        }
        LOGGER.info("Release 1.1");

        // We do the parsing
        Command cmd = new Command();
        CmdLineParser cmdParser = new CmdLineParser(cmd);
        try {
            cmdParser.parseArgument(args);
        } catch (CmdLineException e) {
            LOGGER.error("invalid syntax: {}", e.getLocalizedMessage(), e);
            cmdParser.printUsage(System.out);
            System.exit(ExitCodes.SYNTAX.code());
            throw new RuntimeException(e);
        }

        // We print out the parsing results
        LOGGER.info("selected directory for index={}", cmd.getIndex());
        if (cmd.getDump() != null) {
            LOGGER.info("selected wikipedia dump={}", cmd.getDump());
        }

        // We initialize the index page store
        Index index;
        try {
            index = new Index(cmd.getIndex());
        } catch (IOException e) {
            LOGGER.error("index initialization failed for directory={}", cmd.getIndex(), e);
            System.exit(ExitCodes.INDEX.code());
            throw new RuntimeException(e);
        }

        // We add a shutdown hook to properly shutdown the index
        // when the JVM shuts down (to prevent index damage)
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                index.shutdown();
            } catch (IOException e) {
                LOGGER.error("index shutdown failed for directory={}, index might be corrupted", cmd.getIndex(), e);
            }
        }));

        // Parse if no pages are in the index and a dump is given
        if (cmd.getDump() != null) {
            if (index.getPageCount() > 0) {
                LOGGER.info("index directory={} already contains documents, wikipedia dump will not be processed", cmd.getIndex());
            } else {
                Parser parser;
                parser = new Parser(cmd.getDump(), index);
                try {
                    parser.parse();
                } catch (IOException | ParserConfigurationException | SAXException | PageStoreAddException e) {
                    LOGGER.error("failed to parse wikipedia dump={}", cmd.getDump(), e);
                    System.exit(ExitCodes.PARSE.code());
                    throw new RuntimeException(e);
                }
            }
        }

        // We bring up the HTTP server
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
