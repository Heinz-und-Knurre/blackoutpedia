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

import org.apache.commons.io.IOUtils;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        LOGGER.info("Release 1.2");

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

        if (cmd.getSubCommand() instanceof IndexCommand) {
            MainIndex.exec((IndexCommand) cmd.getSubCommand());
        } else if (cmd.getSubCommand() instanceof KrakenCommand) {
            MainKraken.exec((KrakenCommand) cmd.getSubCommand());
        } else {
            MainServe.exec((ServeCommand) cmd.getSubCommand());
        }
    }
}
