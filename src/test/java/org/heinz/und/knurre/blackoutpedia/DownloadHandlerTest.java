package org.heinz.und.knurre.blackoutpedia;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class DownloadHandlerTest {

    @Test
    public void testDownloadHandler() throws IOException {

        ConcurrentHashMap<String, Boolean> mapLoaded = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, Integer> mapFault = new ConcurrentHashMap<>();
        LinkedBlockingQueue<Path> channels = new LinkedBlockingQueue<>();
        DownloadHandler handler = new DownloadHandler(
                "someHash",
                "Illustration Chenopodium bonus-henricus0 clean.JPG",
                Files.createTempFile("blackoutpedia-destination", ".junit"),
                mapLoaded,
                mapFault,
                Files.createTempDirectory("blackoutpedia-store"),
                channels
        );

        HttpResponse response = mock(HttpResponse.class);
        StatusLine status = mock(StatusLine.class);
        HttpEntity entity = mock(HttpEntity.class);
        doReturn(status).when(response).getStatusLine();
        doReturn(200).when(status).getStatusCode();
        doReturn(entity).when(response).getEntity();

        handler.handleResponse(response);

        assertEquals(1, mapLoaded.size());
        assertEquals(0, mapFault.size());

        doReturn(404).when(status).getStatusCode();
        handler.handleResponse(response);

        assertEquals(1, mapLoaded.size());
        assertEquals(1, mapFault.size());
    }
}
