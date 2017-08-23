package org.heinz.und.knurre.blackoutpedia;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExitCodesTest {

    @Test
    public void testExitCodes() {

        ExitCodes exitCode = ExitCodes.valueOf("INDEX");
        assertEquals(exitCode, ExitCodes.INDEX);
        assertEquals(Integer.valueOf(2), ExitCodes.INDEX.code());
    }
}
