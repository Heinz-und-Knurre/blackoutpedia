package org.heinz.und.knurre.blackoutpedia;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ParserTest {

    @Test
    public void testParser() throws URISyntaxException, ParserConfigurationException, SAXException, IOException {

        Path dumpFile = Paths.get(BZip2Test.class.getResource(Config.TEST_DUMP_RESOURCE).toURI());
        Parser parser = new Parser(dumpFile, new TestIndex());
        parser.parse();
    }
}
