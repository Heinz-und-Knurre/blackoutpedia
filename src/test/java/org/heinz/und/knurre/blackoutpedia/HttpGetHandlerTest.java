package org.heinz.und.knurre.blackoutpedia;

import io.undertow.Handlers;
import io.undertow.Undertow;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class HttpGetHandlerTest {

    private static Undertow server;
    private static PageStoreI pageStore;

    @BeforeClass
    public static void before() {

        pageStore = mock(PageStoreI.class);

        server = Undertow.builder()
                .addHttpListener(44444, "127.0.0.1")
                .setHandler(Handlers.path()
                        .addExactPath("/", new HttpGetHandler(pageStore))
                )
                .build();
        server.start();
    }

    @AfterClass
    public static void after() {
        server.stop();
        pageStore = null;
    }

    @Test
    public void testHandler() throws IOException {

        CloseableHttpClient http = HttpClients.createDefault();

        HttpGet get = new HttpGet("http://localhost:44444/");
        HttpResponse response = http.execute(get);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.getEntity().writeTo(out);

        assertFalse(out.toString().contains("CONTENT"));

        WikipediaPage page1 = new WikipediaPage();
        page1.setId("1");
        page1.setTitle("TITLE1");
        page1.setText("TEXT1");
        doReturn(page1).when(pageStore).get(eq(1));
        doReturn(page1).when(pageStore).get(any(String.class));

        WikipediaPage page2 = new WikipediaPage();
        page2.setId("2");
        page2.setTitle("TITLE2");
        page2.setText("TEXT2");
        doReturn(page2).when(pageStore).get(eq(2));
        doReturn(Arrays.asList(new Integer(2))).when(pageStore).search(any());

        get = new HttpGet("http://localhost:44444/?search=xyz");
        response = http.execute(get);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        out = new ByteArrayOutputStream();
        response.getEntity().writeTo(out);

        assertTrue(out.toString().contains("TITLE2"));

        get = new HttpGet("http://localhost:44444/?id=1");
        response = http.execute(get);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        out = new ByteArrayOutputStream();
        response.getEntity().writeTo(out);

        assertTrue(out.toString().contains("TITLE1"));

        get = new HttpGet("http://localhost:44444/?title=1");
        response = http.execute(get);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        out = new ByteArrayOutputStream();
        response.getEntity().writeTo(out);

        assertTrue(out.toString().contains("TITLE1"));
    }
}
