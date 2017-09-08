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

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Implements the index command bean that is used to store the result of the
 * command line argument parsing for the index sub command.
 */
public final class IndexCommand implements CommandI {

    @Option(name = "-s", aliases = "--store", required = true, usage = "storage directory")
    private Path store;

    @Option(name = "-d", aliases = "--dump", required = true, usage = "wikipedia dump archive to be indexed (xxx-pages-articles.xml.bz2)")
    private Path dump;

    /**
     * @return Storage directory
     */
    public Path getStore() {
        return this.store;
    }

    /**
     * @return Wikipedia dump archive
     */
    public Path getDump() {
        return this.dump;
    }
}
