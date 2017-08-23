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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

final class Parser {

    private static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);

    private final Path dump;
    private final PageStoreI pageStore;

    public Parser(Path dump, PageStoreI pageStore) {
        this.dump = dump;
        this.pageStore = pageStore;
    }

    public Long parse() throws IOException, ParserConfigurationException, SAXException {

        LOGGER.debug("opening bzip2 input stream for wikipedia dump archive file={}", this.dump);
        Long dumpSize = Files.size(this.dump);

        LOGGER.info("wikipedia dump size(MB)={}", (dumpSize / 1024 / 1024));
        CountingInputStream countingIn = new CountingInputStream(Files.newInputStream(this.dump));
        BufferedInputStream bufferedIn = new BufferedInputStream(countingIn);
        BZip2CompressorInputStream bzip2In = new BZip2CompressorInputStream(bufferedIn);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        WikipediaPagesArticlesHandler handler = new WikipediaPagesArticlesHandler(
                this.pageStore,
                new ProgressBar(),
                dumpSize,
                countingIn
        );

        LOGGER.info("wikipedia dump={} ready for parsing", this.dump);

        Long start = System.currentTimeMillis();
        LOGGER.info("start parsing timestamp={}", start);
        parser.parse(bzip2In, handler);

        Long duration = System.currentTimeMillis() - start;
        LOGGER.info("finished parsing, duration(min)={}", (duration / 6000));
        return duration;
    }
}
