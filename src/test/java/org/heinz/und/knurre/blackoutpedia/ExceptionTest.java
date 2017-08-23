package org.heinz.und.knurre.blackoutpedia;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExceptionTest {

    @Test
    public void testExceptions() {

        Exception cause = new Exception("123");
        PageStoreAddException e1 = new PageStoreAddException(cause);
        PageStoreGetException e2 = new PageStoreGetException(cause);
        PageStoreParseException e3 = new PageStoreParseException(cause);
        PageStoreQueryException e4 = new PageStoreQueryException(cause);
        RenderException e5 = new RenderException(cause);
        UTF8EncodingException e6 = new UTF8EncodingException(cause);

        assertEquals(cause, e1.getCause());
        assertEquals(cause, e2.getCause());
        assertEquals(cause, e3.getCause());
        assertEquals(cause, e4.getCause());
        assertEquals(cause, e5.getCause());
        assertEquals(cause, e6.getCause());
    }
}
