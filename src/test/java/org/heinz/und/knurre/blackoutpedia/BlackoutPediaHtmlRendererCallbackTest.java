package org.heinz.und.knurre.blackoutpedia;

import org.junit.Test;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.parser.nodes.WtUrl;

import java.io.UnsupportedEncodingException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class BlackoutPediaHtmlRendererCallbackTest {

    @Test
    public void testRendererCallback() {

        BlackoutPediaHtmlRendererCallback callback = new BlackoutPediaHtmlRendererCallback("SEARCH");

        assertNull(callback.getMediaInfo("title", 100, 200));

        PageTitle title = mock(PageTitle.class);
        doReturn("title").when(title).getTitle();

        assertTrue(callback.resourceExists(title));

        assertEquals("?title=title&search=SEARCH", callback.makeUrl(title));

        WtUrl target = mock(WtUrl.class);
        assertEquals("null:null", callback.makeUrl(target));

        assertEquals("?search=SEARCH", callback.makeUrlMissingTarget("target"));
    }
}
