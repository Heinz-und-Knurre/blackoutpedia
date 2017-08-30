package org.heinz.und.knurre.blackoutpedia;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileLinkText {

    @Test
    public void testFileLink() {

        Pattern p = Pattern.compile("\\[\\[File:([^|\\]]*)");
        String file1 = "abcd[[File:filename.extension|options|caption]]efgh";
        Matcher m = p.matcher(file1);
        assertTrue(m.find());
        assertEquals("filename.extension", m.group(1));
        String file2 = "abcd[[File:file1.sdx]]efgh";
        m = p.matcher(file2);
        assertTrue(m.find());
        assertEquals("file1.sdx", m.group(1));
    }
}
