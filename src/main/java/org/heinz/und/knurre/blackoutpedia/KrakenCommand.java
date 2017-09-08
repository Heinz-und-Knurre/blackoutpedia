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
 * Implements the kraken command bean that is used to store the result of the
 * command line argument parsing for the kraken sub command.
 */
public final class KrakenCommand implements CommandI {

    @Option(name = "-s", aliases = "--store", required = true, usage = "storage directory")
    private Path store;

    @Option(name = "-l", aliases = "--language", required = true, usage = "language of the wikipedia dump archive")
    private Languages language;

    @Option(name = "-c", aliases = "--channels", usage = "number channels that download in parallel, defaults: 50")
    private Integer channels = 50;

    @Option(name = "-m", aliases = "--max", usage = "maximum number of files to be downloaded, defaults: 10.000")
    private Integer max = 10000;


    /**
     * @return Storage directory
     */
    public Path getStore() {
        return this.store;
    }

    /**
     * @return Language of the wikipedia dump
     */
    public Languages getLanguage() {
        return language;
    }

    /**
     * @return Number of channels for parallel download
     */
    public Integer getChannels() {
        return this.channels;
    }

    /**
     * @return Maxmimum number of files downloaded
     */
    public Integer getMax() {
        return max;
    }
}
