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

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class LuceneTest {

    static String TEST_TEXT = "<b>Administration</b>This is the text to be indexed";

    @Test
    public void testLucene() throws IOException, ParseException {

        Analyzer analyzer = new StandardAnalyzer();
        Directory directory = new RAMDirectory();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(directory, config);
        Document doc = new Document();
        doc.add(new Field("field", TEST_TEXT, TextField.TYPE_STORED));
        writer.addDocument(doc);
        writer.close();

        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        QueryParser parser = new QueryParser("field", analyzer);
        Query query = parser.parse("Admin*");
        TopDocs hits = searcher.search(query, 1000);
        assertEquals(1, hits.scoreDocs.length);
        assertEquals(TEST_TEXT, searcher.doc(hits.scoreDocs[0].doc).get("field"));
    }
}
