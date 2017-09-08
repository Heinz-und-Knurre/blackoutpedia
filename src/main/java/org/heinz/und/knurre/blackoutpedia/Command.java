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

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.SubCommand;
import org.kohsuke.args4j.spi.SubCommandHandler;
import org.kohsuke.args4j.spi.SubCommands;

import java.nio.file.Path;

/**
 * Implements the command bean that is used to store the result of the
 * command line argument parsing.
 */
final class Command implements CommandI {

    @Argument(metaVar = "sub command", required = true, usage = "{index|kraken|serve}", handler = SubCommandHandler.class)
    @SubCommands({
            @SubCommand(name = "index", impl = IndexCommand.class),
            @SubCommand(name = "kraken", impl = KrakenCommand.class),
            @SubCommand(name = "serve", impl = ServeCommand.class),
    })
    private CommandI subCommand;

    /**
     * @return Sub command
     */
    public CommandI getSubCommand() {
        return this.subCommand;
    }
}
