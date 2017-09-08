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
