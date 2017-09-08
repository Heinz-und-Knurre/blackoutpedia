package org.heinz.und.knurre.blackoutpedia;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class RendererTest {

    @Test
    public void testRenderer() {

        PageStoreI pageStore = mock(PageStoreI.class);

        String html = Renderer.instance().render(
                pageStore,
                Collections.EMPTY_LIST,
                "CONTENT",
                "SEARCH"
        );

        assertNotNull(html);
        assertTrue(html.contains("CONTENT"));

        WikipediaPage page = new WikipediaPage();
        page.setId("1");
        page.setTitle("TITLE");
        page.setText("TEXT");

        doReturn(page).when(pageStore).get(any(Integer.class));

        html = Renderer.instance().render(
                pageStore,
                Arrays.asList(new Integer(1)),
                "CONTENT",
                "SEARCH"
        );

        assertTrue(html.contains("TITLE"));
    }
}
