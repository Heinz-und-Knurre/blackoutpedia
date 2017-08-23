package org.heinz.und.knurre.blackoutpedia;

import org.junit.Test;

public class ProgressBarTest {

    @Test
    public void testProgressBar(){

        ProgressBar bar = new ProgressBar();
        bar.progress(5L,10L,10L);
        bar.progress(5L,10L,10L);
        bar.progress(9L,10L,11L);
    }
}
