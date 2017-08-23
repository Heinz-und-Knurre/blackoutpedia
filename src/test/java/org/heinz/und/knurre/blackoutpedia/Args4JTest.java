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

import org.junit.Test;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

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
        parser.parseArgument("--index", "/", "--dump", "/test.dump");

        assertEquals(Paths.get("/"), cmd.getIndex());
        assertEquals(Paths.get("/test.dump"), cmd.getDump());
        assertEquals(Integer.valueOf(8080), cmd.getPort());
        assertEquals("127.0.0.1", cmd.getBind());
    }
}
