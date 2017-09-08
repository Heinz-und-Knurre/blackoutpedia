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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class TestIndex implements PageStoreI {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestIndex.class);

    private Long count = 0L;

    @Override
    public void add(String id, WikipediaPage page) {
        this.count++;
        LOGGER.debug("add page id={}", id);
    }

    @Override
    public Integer getPageCount() {
        return 0;
    }

    @Override
    public List<Integer> search(String query) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public WikipediaPage get(Integer id) {
        return new WikipediaPage();
    }

    @Override
    public WikipediaPage get(String title) {
        return new WikipediaPage();
    }

    public Long getCount() {
        return count;
    }
}
