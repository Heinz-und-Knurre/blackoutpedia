package org.heinz.und.knurre.blackoutpedia;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class IndexTest {

    @Test
    public void testIndex() throws IOException {

        Path storage = Files.createTempDirectory("blackoutpedia-junit");
        Index index = new Index(storage);
        assertEquals(new Integer(0), index.getPageCount());

        WikipediaPage page = new WikipediaPage();
        page.setId("1");
        page.setTitle("TITLE1");
        page.setText("TEXT1");

        index.add("1", page);
        index.shutdown();

        index = new Index(storage);
        assertEquals(new Integer(1), index.getPageCount());

        WikipediaPage result = index.get(0);
        assertEquals("1", result.getId());
        assertEquals("TITLE1", result.getTitle());
        assertEquals("TEXT1", result.getText());

        result = index.get("TITLE1");
        assertEquals("1", result.getId());
        assertEquals("TITLE1", result.getTitle());
        assertEquals("TEXT1", result.getText());

        List<Integer> hits = index.search("TEXT1");
        assertEquals(1, hits.size());
        assertEquals(new Integer(0), hits.get(0));

        hits = index.search("PANNE");
        assertEquals(0, hits.size());

        try {
            index.get("PANNE");
            fail();
        }catch(PageStoreGetException e){
            // What we expect
        }


    }
}
