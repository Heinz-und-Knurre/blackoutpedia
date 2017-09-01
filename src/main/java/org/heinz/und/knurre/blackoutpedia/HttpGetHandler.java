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

import de.fau.cs.osr.utils.FmtNotYetImplementedError;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;
import org.commonmark.node.Node;
import org.commonmark.parser.*;
import org.commonmark.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sweble.wikitext.engine.PageId;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.engine.WtEngineImpl;
import org.sweble.wikitext.engine.config.WikiConfig;
import org.sweble.wikitext.engine.nodes.EngProcessedPage;
import org.sweble.wikitext.engine.output.HtmlRenderer;
import org.sweble.wikitext.engine.output.HtmlRendererCallback;
import org.sweble.wikitext.engine.output.MediaInfo;
import org.sweble.wikitext.engine.utils.DefaultConfigEnWp;
import org.sweble.wikitext.parser.nodes.WtUrl;

import java.io.StringWriter;
import java.util.Collections;
import java.util.List;

/**
 * Servlet implementation that does the rendering. Currently we have no server side
 * session where we could store last search results so we make sure the search query
 * is always injected into the input so we get on the next call.
 */
public class HttpGetHandler implements HttpHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpGetHandler.class);

    private final PageStoreI pageStore;
    private final WikiConfig config;
    private final WtEngineImpl wtEngine;

    public HttpGetHandler(PageStoreI pageStore) {
        this.pageStore = pageStore;
        this.config = DefaultConfigEnWp.generate();
        this.wtEngine = new WtEngineImpl(this.config);
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {

        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html; charset=utf-8");

        // Grab the search query parameter
        String paramSearch = exchange.getQueryParameters().get("search") == null
                ? null
                : exchange.getQueryParameters().get("search").peek();
        // Grab the id query parameter
        String paramId = exchange.getQueryParameters().get("id") == null
                ? null
                : exchange.getQueryParameters().get("id").peek();

        // We load a page if we have a id parameter
        WikipediaPage page = paramId == null ? null : this.pageStore.get(Integer.valueOf(paramId));

        // We search for hits if we have a query, or hits is an empty list
        List<Integer> hits = paramSearch == null
                ? Collections.EMPTY_LIST
                : this.pageStore.search(exchange.getQueryParameters().get("search").peek());

        StringBuilder html = new StringBuilder();

        // We only HTML convert content if we have a page
        if (page != null) {
            html.append("<h1>");
            html.append(page.getTitle());
            html.append("</h1>\n");
            // In the unlikely event of UTF8 not being able for encoding
            // we will render a page with title only
            try {
                PageTitle pageTitle = PageTitle.make(config, page.getTitle());
                PageId pageId = new PageId(pageTitle, -1);
                EngProcessedPage cp = this.wtEngine.postprocess(pageId, page.getText(), null);
                html.append(
                        HtmlRenderer.print(
                                new BlackoutPediaHtmlRendererCallback(this.pageStore, paramSearch),
                                this.config,
                                pageTitle,
                                cp.getPage()
                        )
                );
            } catch (Throwable e) {
                LOGGER.error("failed to render wiki text for page={}", page.getTitle(), e);
            }
        }

        try {
            String result = Renderer.instance().render(pageStore, hits, html.toString(), paramSearch);
            exchange.setStatusCode(StatusCodes.OK);
            exchange.getResponseSender().send(result);
        } catch (RenderException e) {
            exchange.setStatusCode(StatusCodes.INTERNAL_SERVER_ERROR);
            exchange.getResponseSender().send(ERROR_PAGE);
        }
    }

    private static final String ERROR_PAGE =
            "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "\t<meta charset=\"utf-8\">\n" +
                    "\t<title>BlackoutPedia - Error</title>\n" +
                    "\t<style>\n" +
                    "\t\tbody {\n" +
                    "\t\t\tfont-family: sans-serif;\n" +
                    "\t\t}\n" +
                    "\t\tp {\n" +
                    "\t\t\tpadding-left: 1em;\n" +
                    "\t\t\tpadding-right: 1em;\n" +
                    "\t\t\tline-height: 1.2;\n" +
                    "\t\t}\n" +
                    "\t\t#divError {\n" +
                    "\t\t\tfloat: left;\n" +
                    "\t\t\theight: 100%;\n" +
                    "\t\t\tpadding: 1em;\n" +
                    "\t\t\twidth: 20%;\n" +
                    "\t\t}\n" +
                    "\t\t#pError {\n" +
                    "\t\t\tbackground-color: #cc0000;\n" +
                    "\t\t\tbackground-image: url(data:image/svg+xml;base64,PHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4IiB2aWV3Qm94PSIwIDAgNTIgNTIiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUyIDUyOyIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSI+PHN0eWxlIHR5cGU9InRleHQvY3NzIj4uc3Qwe2ZpbGw6I0ZGRkZGRjt9PC9zdHlsZT48cGF0aCBjbGFzcz0ic3QwIiBkPSJNNDQuNCw3LjZDMzQuMi0yLjUsMTcuNy0yLjUsNy42LDcuNnMtMTAuMSwyNi42LDAsMzYuOHMyNi42LDEwLjEsMzYuOCwwQzU0LjUsMzQuMiw1NC41LDE3LjcsNDQuNCw3LjZ6IE0zNi4yLDM2LjJjLTAuOCwwLjgtMiwwLjgtMi44LDBMMjYsMjguOGwtNy44LDcuOGMtMC44LDAuOC0yLDAuOC0yLjgsMGMtMC44LTAuOC0wLjgtMiwwLTIuOGw3LjgtNy44bC03LjQtNy40Yy0wLjgtMC44LTAuOC0yLDAtMi44YzAuOC0wLjgsMi0wLjgsMi44LDBsNy40LDcuNGw3LjEtNy4xYzAuOC0wLjgsMi0wLjgsMi44LDBzMC44LDIsMCwyLjhMMjguOCwyNmw3LjQsNy40QzM3LDM0LjIsMzcsMzUuNSwzNi4yLDM2LjJ6Ii8+PC9zdmc+);\n" +
                    "\t\t\tbackground-position: left 1em center;\n" +
                    "\t\t\tbackground-repeat: no-repeat;\n" +
                    "\t\t\tbackground-size: 2em 2em;\n" +
                    "\t\t\tborder-radius: 0.5em;\n" +
                    "\t\t\tcolor: white;\n" +
                    "\t\t\tdisplay: block;\n" +
                    "\t\t\tfont-size: 1.2em;\n" +
                    "\t\t\tpadding: 1em;\n" +
                    "\t\t\ttext-align: center;\n" +
                    "\t\t}\n" +
                    "\t</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\t<div id=\"divError\">\n" +
                    "\t\t<p id=\"pError\">Error</p>\n" +
                    "\t\t<p>Something is wrong with your installation of BlackoutPedia.</p>\n" +
                    "\t\t<p>The server failed to render the requested page.</p>\n" +
                    "\t\t<p>Please check the log file for further details and contact us in case you cannot resolve the issue.</p>\n" +
                    "\t</div>\n" +
                    "</body>";
}
