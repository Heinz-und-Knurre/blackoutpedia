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

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;

/**
 * Provides the CSS stylesheets to the browser.
 */
public class CssHandler implements HttpHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CssHandler.class);

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {

        InputStream css;
        // Depending on the query string load the main or the wiki CSS
        if (exchange.getQueryString().endsWith("main")) {
            LOGGER.debug("retrieving main CSS");
            css = Main.class.getResourceAsStream("/main.css");
        } else {
            LOGGER.debug("retrieving wiki CSS");
            css = Main.class.getResourceAsStream("/wiki.css");
        }
        StringBuilder result = new StringBuilder();
        List<String> lines = IOUtils.readLines(css);
        for (String line : lines) {
            result.append(line);
            result.append("\n");
        }

        exchange.setStatusCode(StatusCodes.OK);
        exchange.getResponseSender().send(result.toString());
    }
}
