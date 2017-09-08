package org.heinz.und.knurre.blackoutpedia;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpServerExchange;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CssHandlerTest {

    private static Undertow server;

    @BeforeClass
    public static void before() {
        server = Undertow.builder()
                .addHttpListener(44444, "127.0.0.1")
                .setHandler(Handlers.path()
                        .addExactPath("/css", new CssHandler())
                )
                .build();
        server.start();
    }

    @AfterClass
    public static void after() {
        server.stop();
    }

    @Test
    public void testCssHandler() throws Exception {

        CloseableHttpClient http = HttpClients.createDefault();

        HttpGet getMain = new HttpGet("http://localhost:44444/css?main");
        HttpResponse responseMain = http.execute(getMain);
        assertEquals(HttpStatus.SC_OK, responseMain.getStatusLine().getStatusCode());
        ByteArrayOutputStream outMain = new ByteArrayOutputStream();
        responseMain.getEntity().writeTo(outMain);
        assertTrue(outMain.toString().startsWith("/* main.css"));

        HttpGet getWiki = new HttpGet("http://localhost:44444/css?wiki");
        HttpResponse responseWiki = http.execute(getWiki);
        assertEquals(HttpStatus.SC_OK, responseWiki.getStatusLine().getStatusCode());
        ByteArrayOutputStream outWiki = new ByteArrayOutputStream();
        responseWiki.getEntity().writeTo(outWiki);
        assertTrue(outWiki.toString().startsWith("/* wiki.css"));

        HttpGet getBad = new HttpGet("http://localhost:44444/css");
        HttpResponse responseBad = http.execute(getBad);
        assertEquals(HttpStatus.SC_OK, responseBad.getStatusLine().getStatusCode());
        ByteArrayOutputStream outBad = new ByteArrayOutputStream();
        responseBad.getEntity().writeTo(outBad);
        assertTrue(outBad.toString().startsWith("/* wiki.css"));
    }
}
