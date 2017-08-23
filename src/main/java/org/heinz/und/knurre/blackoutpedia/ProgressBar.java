/**
 * Copyright © 2017 Heinz&Knurre (andreas.gorbach@gmail.com christian.d.middel@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.heinz.und.knurre.blackoutpedia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Server implementation of the progress listener for the index load
 * bulk transaction that supports displaying of overall progress in
 * percent and the expected time of arival in minutes.
 */
public class ProgressBar implements ProgressListenerI {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgressBar.class);

    private Long start = 0L;
    private Long lastProgress = 0L;

    @Override
    public void progress(Long byteCount, Long dumpSize, Long pageCount) {

        if (this.start == 0L) {
            this.start = System.currentTimeMillis();
        }

        Long progress = (byteCount * 100) / dumpSize;

        if (this.lastProgress < progress) {
            this.lastProgress = progress;
            Long duration = System.currentTimeMillis() - this.start;
            Long eta = ((dumpSize - byteCount) * duration) / byteCount;
            LOGGER.info("progress={}%, eta(min)={}, with pageCount={}", progress, (eta / 60000), pageCount);
            this.lastProgress = progress;
        }
    }
}
