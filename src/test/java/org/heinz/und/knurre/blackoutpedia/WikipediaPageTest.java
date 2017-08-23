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
