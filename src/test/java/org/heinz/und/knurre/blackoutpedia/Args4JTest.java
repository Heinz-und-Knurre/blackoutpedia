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

import org.junit.Test;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Args4JTest {

    @Test
    public void testArgs4J() throws CmdLineException {

        TestMain main = new TestMain();
        CmdLineParser parser = new CmdLineParser(main);
        parser.printUsage(System.out);

        parser.parseArgument("--data", "/");

        assertEquals(Paths.get("/"), main.getDataFolder());

        Command cmd = new Command();
        parser = new CmdLineParser(cmd);
        parser.parseArgument("index", "--store", "/", "--dump", "/test.dump");

        assertTrue(cmd.getSubCommand() instanceof IndexCommand);
        IndexCommand indexCmd = (IndexCommand) cmd.getSubCommand();
        assertEquals(Paths.get("/"), indexCmd.getStore());
        assertEquals(Paths.get("/test.dump"), indexCmd.getDump());

        parser.parseArgument("serve", "--store", "/");

        assertTrue(cmd.getSubCommand() instanceof ServeCommand);
        ServeCommand serveCmd = (ServeCommand) cmd.getSubCommand();
        assertEquals(Paths.get("/"), serveCmd.getStore());
        assertEquals(Integer.valueOf(8080), serveCmd.getPort());
        assertEquals("127.0.0.1", serveCmd.getBind());

        parser.parseArgument("kraken", "--store", "/", "--language", "EN");

        assertTrue(cmd.getSubCommand() instanceof KrakenCommand);
        KrakenCommand krakenCmd = (KrakenCommand) cmd.getSubCommand();
        assertEquals(Paths.get("/"), krakenCmd.getStore());
        assertEquals(Languages.EN, krakenCmd.getLanguage());
        assertEquals(Integer.valueOf(50), krakenCmd.getChannels());
        assertEquals(Integer.valueOf(10000), krakenCmd.getMax());
    }
}
