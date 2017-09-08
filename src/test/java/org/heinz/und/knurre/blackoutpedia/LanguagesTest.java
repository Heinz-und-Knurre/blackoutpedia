package org.heinz.und.knurre.blackoutpedia;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LanguagesTest {

    @Test
    public void testLanguages() {

        Languages l = Languages.valueOf("DE");
        assertEquals(Languages.DE, l);
    }
}
