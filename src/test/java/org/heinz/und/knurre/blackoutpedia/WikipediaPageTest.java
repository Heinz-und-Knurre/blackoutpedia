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

import org.junit.Test;

import static org.junit.Assert.*;

public class WikipediaPageTest {

    @Test
    public void testWikipediaPage() {

        WikipediaPage page1 = new WikipediaPage();
        page1.setId("1");
        page1.setTitle("title1");
        page1.setText("text1");

        assertEquals("1", page1.getId());
        assertEquals("title1", page1.getTitle());
        assertEquals("text1", page1.getText());

        WikipediaPage page2 = new WikipediaPage();
        page2.setId("1");
        page2.setTitle("title1");
        page2.setText("text1");

        assertTrue(page1.equals(page2));
        assertTrue(page1.equals(page1));
        assertFalse(page1.equals(null));
        assertFalse(page1.equals("123"));

        assertEquals(page1.toString(), page2.toString());

        assertEquals(80, page1.hashCode());
    }
}
