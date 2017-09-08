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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Renders the HTML page.
 */
final class Renderer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Renderer.class);

    private static final Renderer INSTANCE = new Renderer();

    private final String template;

    public String render(PageStoreI pageStore, List<Integer> hits, String content, String search) {

        // Then render the links for the search results
        StringBuilder results = new StringBuilder("<ul id=\"ulResult\"\">\n");
        for (Integer id : hits) {
            WikipediaPage page = pageStore.get(id);
            results.append("<li>");
            results.append("<a href=\"/?id=");
            results.append(id);
            results.append("&search=");
            try {
                results.append(URLEncoder.encode(search, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("failed to render search result links", e);
                throw new RenderException(new UTF8EncodingException(e));
            }
            results.append("\">");
            results.append(page.getTitle());
            results.append("</a>");
            results.append("</li>");
            results.append("\n");
        }

        // Now inject the results, the content and the search input text
        return this.template
                .replace("%result", results.toString())
                .replace("%content", content)
                .replace("%search", search == null ? "" : search);
    }

    private Renderer() {

        // First load the template
        StringBuilder html = new StringBuilder();
        try {
            List<String> lines = IOUtils.readLines(Renderer.class.getResourceAsStream("/main.html"));
            for (String line : lines) {
                html.append(line);
                html.append("\n");
            }
            this.template = html.toString();
        } catch (IOException e) {
            LOGGER.error("failed to load main HTML template", e);
            throw new RenderException(new UTF8EncodingException(e));
        }
    }

    public static final Renderer instance() {
        return INSTANCE;
    }
}
