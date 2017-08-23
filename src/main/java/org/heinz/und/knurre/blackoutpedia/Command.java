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

import org.kohsuke.args4j.Option;

import java.nio.file.Path;

/**
 * Implements the command bean that is used to store the result of the
 * command line argument parsing.
 */
final class Command {

    @Option(name = "-i", aliases = "--index", required = true, usage = "index directory")
    private Path index;

    @Option(name = "-d", aliases = "--dump", usage = "wikipedia dump")
    private Path dump;

    @Option(name = "-p", aliases = "--port", usage = "http port, defaults: 8080")
    private Integer port = 8080;

    @Option(name = "-b", aliases = "--bind", usage = "bind address, defaults: 127.0.0.1")
    private String bind = "127.0.0.1";


    /**
     * @return Index directory
     */
    public Path getIndex() {
        return this.index;
    }

    /**
     * @return Wikipedia dump
     */
    public Path getDump() {
        return this.dump;
    }

    /**
     * @return HTTP port
     */
    public Integer getPort() {
        return this.port;
    }

    /**
     * @return Bind address
     */
    public String getBind() {
        return this.bind;
    }
}
