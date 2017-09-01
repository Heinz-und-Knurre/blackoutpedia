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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.engine.output.HtmlRendererCallback;
import org.sweble.wikitext.engine.output.MediaInfo;
import org.sweble.wikitext.parser.nodes.WtUrl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class BlackoutPediaHtmlRendererCallback implements HtmlRendererCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlackoutPediaHtmlRendererCallback.class);

    private final PageStoreI pageStore;
    private final String search;

    public BlackoutPediaHtmlRendererCallback(PageStoreI pageStore, String search) {
        this.pageStore = pageStore;
        this.search = search;
    }

    @Override
    public MediaInfo getMediaInfo(String title, int width, int height) {

        LOGGER.debug("getMediaInfo title={}, width={}, height={}", title, width, height);
        return null;
    }

    @Override
    public boolean resourceExists(PageTitle target) {

        LOGGER.debug("resourceExists pageTitle={}", target.getTitle());
        return true;
    }

    @Override
    public String makeUrl(PageTitle target) {

        LOGGER.debug("makeUrl pageTitle={}", target.getTitle());
        try {
            return "?title=" + URLEncoder.encode(target.getTitle()) + "&search=" + URLEncoder.encode(this.search, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("UTF8 not supported when encoding wiki page url for target={}", target.getTitle(), e);
            throw new UTF8EncodingException(e);
        }
    }

    @Override
    public String makeUrl(WtUrl target) {

        LOGGER.debug("makeUrl target={}", target);
        return target.getProtocol() == ""
                ? target.getPath()
                : target.getProtocol() + ":" + target.getPath();
    }

    @Override
    public String makeUrlMissingTarget(String target) {

        LOGGER.debug("makeUrlMissingTarget target={}", target);
        try {
            return "?search=" + URLEncoder.encode(this.search, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("UTF8 not supported when encoding wiki missing page url for target={}", target, e);
            throw new UTF8EncodingException(e);
        }
    }
}
