/**
 * Copyright © 2017 Heinz&Knurre (andreas.gorbach@gmail.com christian.d.middel@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.heinz.und.knurre.blackoutpedia;

import com.google.common.io.CountingInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class WikipediaPagesArticlesHandlerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikipediaPagesArticlesHandlerTest.class);

    @Test
    public void testHandler() throws ParserConfigurationException, SAXException, URISyntaxException, IOException {

        Long start = System.currentTimeMillis();

        Path dumpFile = Paths.get(BZip2Test.class.getResource(Config.TEST_DUMP_RESOURCE).toURI());
        Long dumpSize = Files.size(dumpFile);
        CountingInputStream countingIn = new CountingInputStream(Files.newInputStream(dumpFile));
        BufferedInputStream bufferedIn = new BufferedInputStream(countingIn);
        BZip2CompressorInputStream bzip2In = new BZip2CompressorInputStream(bufferedIn);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        TestIndex index = new TestIndex();
        WikipediaPagesArticlesHandler handler = new WikipediaPagesArticlesHandler(
                index,
                new ProgressListenerI() {
                    private Long start = 0L;
                    private Long lastProgress = 0L;

                    @Override
                    public void progress(Long byteCount, Long dumpSize, Long pageCount) {
                        if (this.start == 0L) {
                            this.start = System.currentTimeMillis();
                        }
                        Long progress = (byteCount * 100) / dumpSize;
                        if (this.lastProgress < progress) {
                            Long duration = System.currentTimeMillis() - this.start;
                            Long eta = ((dumpSize - byteCount) * duration) / byteCount;
                            LOGGER.info("progress={}%, eta(min)={}, with pageCount={}", progress, (eta / 60000), pageCount);
                            this.lastProgress = progress;
                        }

                    }
                },
                dumpSize,
                countingIn
        );

        parser.parse(bzip2In, handler);

        LOGGER.info("time for parsing pages-articles from bzip2 in seconds={}", ((System.currentTimeMillis() - start) / 1000));

        assertEquals(Long.valueOf(39303), index.getCount());
    }
}
