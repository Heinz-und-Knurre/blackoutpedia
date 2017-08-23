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

import com.google.common.io.CountingInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Stack;

/**
 * Implements the SAX parser handler that collects all relevant
 * Wikipedia page information and sends it to a page store
 * for persisting it.
 */
final class WikipediaPagesArticlesHandler extends DefaultHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(WikipediaPagesArticlesHandler.class);

    private final static String PAGE = "page";
    private final static String ID = "id";
    private final static String TITLE = "title";
    private final static String REVISION = "revision";
    private final static String TEXT = "text";

    private final PageStoreI pageStore;
    private final ProgressListenerI progressListener;
    private final Long dumpSize;
    private final CountingInputStream countingInputStream;
    private final Stack<WikipediaPage> pageStack = new Stack<>();
    private final Stack<String> tagStack = new Stack<>();
    private StringBuilder buffer;
    private Long pageCounter = 0L;

    public WikipediaPagesArticlesHandler(
            PageStoreI pageStore, ProgressListenerI progressListener, Long dumpSize,
            CountingInputStream countingInputStream) {

        this.pageStore = pageStore;
        this.progressListener = progressListener;
        this.dumpSize = dumpSize;
        this.countingInputStream = countingInputStream;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        LOGGER.trace("startElement uri={}, localName={}, qName={}", uri, localName, qName);

        if (PAGE.equals(qName)) {
            WikipediaPage page = new WikipediaPage();
            this.pageStack.push(page);
        }

        this.tagStack.push(qName);
        this.buffer = new StringBuilder();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        buffer.append(new String(ch, start, length));
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        LOGGER.trace("endElement uri={}, localName={}, qName={}", uri, localName, qName);

        String removed = this.tagStack.pop();

        if (PAGE.equals(removed)) {
            WikipediaPage page = this.pageStack.pop();
            this.pageStore.add(page.getId(), page);
            this.pageCounter++;
            this.progressListener.progress(this.countingInputStream.getCount(), this.dumpSize, this.pageCounter);
        }

        if (ID.equals(removed) && PAGE.equals(this.tagStack.peek())) {
            pageStack.peek().setId(this.buffer.toString());
        }
        if (TITLE.equals(removed) && PAGE.equals(this.tagStack.peek())) {
            pageStack.peek().setTitle(this.buffer.toString());
        }
        if (TEXT.equals(removed) && REVISION.equals(this.tagStack.peek())) {
            pageStack.peek().setText(this.buffer.toString());
        }
    }
}
