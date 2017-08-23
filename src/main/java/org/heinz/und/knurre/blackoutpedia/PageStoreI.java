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

import java.util.List;

/**
 * Defines the interface for any implementation that will be able to
 * persist Wikipedia pages and allow for later on search and retrieval.
 */
public interface PageStoreI {

    /**
     * Adds a new Wikipedia page to the store.
     *
     * @param id   Page identifier
     * @param page Page details
     */
    void add(String id, WikipediaPage page);

    /**
     * @return Number of Wikipedia pages persisted in the store
     */
    Integer getPageCount();

    /**
     * Searches the store for Wikipedia pages matching where the
     * text content matches the specified query.
     *
     * @param query Query in lucene format
     * @return List of store specific identifiers that match
     */
    List<Integer> search(String query);

    /**
     * Retrieves the specified page from the store.
     *
     * @param id Store specified identifier
     * @return Wikipedia page
     */
    WikipediaPage get(Integer id);

    /**
     * Retrieve the store identifier for the specified title.
     * @param title Title
     * @return Store identifier
     */
    Integer getTitleId(String title);
}
