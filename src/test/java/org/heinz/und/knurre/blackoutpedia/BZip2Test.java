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

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class BZip2Test {

    @Test
    public void testDecompress() throws IOException, URISyntaxException {

        Long start = System.currentTimeMillis();

        Path dumpFile = Paths.get(BZip2Test.class.getResource(Config.TEST_DUMP_RESOURCE).toURI());
        BufferedInputStream bufferedIn = new BufferedInputStream(Files.newInputStream(dumpFile));
        BZip2CompressorInputStream bzip2In = new BZip2CompressorInputStream(bufferedIn);

        final byte[] buffer = new byte[Config.BUFFER_SIZE];
        int n = 0;
        Long count = 0L;
        while (-1 != (n = bzip2In.read(buffer))) {
            count = count + n;
        }
        bzip2In.close();
        System.out.println("time for reading bzip2 in seconds=" + ((System.currentTimeMillis() - start) / 1000));
        System.out.println("mb read=" + (count / 1024 / 1024));

        assertEquals(Long.valueOf(59686857), count);
    }
}
