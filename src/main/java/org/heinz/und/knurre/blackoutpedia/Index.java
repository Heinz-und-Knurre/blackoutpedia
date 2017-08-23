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
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the index that is used to store the Wikipedia pages
 * after converting them into HTML format. The index allows us
 * to full-text search the Wikipedia content.
 */
final class Index implements PageStoreI {

    private static final Logger LOGGER = LoggerFactory.getLogger(Index.class);

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String TEXT = "text";

    private final Path directory;
    private final Analyzer analyzer = new StandardAnalyzer();
    private final Directory fsDirectory;
    private final IndexWriterConfig config;
    private final IndexWriter writer;
    private final IndexReader reader;
    private final IndexSearcher searcher;

    public Index(Path directory) throws IOException {

        // We initialize a lucene directory that automatically
        // detects the best implementation for the given
        // environment
        this.directory = directory;
        LOGGER.debug("initializing index in directory={}", this.directory);
        this.fsDirectory = FSDirectory.open(this.directory);

        LOGGER.debug("initializing index config");
        this.config = new IndexWriterConfig(analyzer);
        this.config.setCommitOnClose(Boolean.TRUE);

        LOGGER.debug("initializing index writer");
        this.writer = new IndexWriter(fsDirectory, config);

        LOGGER.debug("initializing index reader");
        this.reader = DirectoryReader.open(this.writer);

        LOGGER.debug("initializing index searcher");
        this.searcher = new IndexSearcher(reader);

        LOGGER.info("index initialized for directory={}", this.directory);
        LOGGER.info("index contains {} document(s)", this.reader.numDocs());
    }

    @Override
    public void add(String id, WikipediaPage page) {

        LOGGER.debug("indexing page id={}", id);

        Document doc = new Document();
        doc.add(new Field(ID, page.getId(), TextField.TYPE_STORED));
        doc.add(new Field(TITLE, page.getTitle(), TextField.TYPE_STORED));
        doc.add(new Field(TEXT, page.getText(), TextField.TYPE_STORED));

        try {
            this.writer.addDocument(doc);
        } catch (IOException e) {
            LOGGER.error("adding index page with id={} failed", id, e);
            throw new PageStoreAddException(e);
        }
    }

    @Override
    public Integer getPageCount() {
        return this.reader.numDocs();
    }

    @Override
    public List<Integer> search(String query) {

        LOGGER.debug("index search with query={}", query);
        QueryParser parser = new QueryParser(TEXT, this.analyzer);
        Query q;
        try {
            q = parser.parse(query);
        } catch (ParseException e) {
            LOGGER.error("parsing of query={} failed", query, e);
            throw new PageStoreParseException(e);
        }
        TopDocs hits;
        try {
            hits = this.searcher.search(q, 10);
        } catch (IOException e) {
            LOGGER.error("index search for query={} failed", query, e);
            throw new PageStoreQueryException(e);
        }
        List<Integer> result = new ArrayList<>();
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            result.add(scoreDoc.doc);
        }

        LOGGER.debug("index search found hits={}", result);
        return result;
    }

    @Override
    public WikipediaPage get(Integer id) {

        LOGGER.debug("index retrieval of document id={}", id);
        WikipediaPage result = new WikipediaPage();
        Document document = null;
        try {
            document = this.searcher.doc(id);
        } catch (IOException e) {
            LOGGER.error("retrieving index document id={} failed", id, e);
            throw new PageStoreGetException(e);
        }
        result.setId(document.get(ID));
        result.setTitle(document.get(TITLE));
        result.setText(document.get(TEXT));
        return result;
    }

    @Override
    public Integer getTitleId(String title) {

        LOGGER.debug("index id retrieval of document with title={}", title);
        QueryParser parser = new QueryParser(TITLE, this.analyzer);
        Query query = parser.createPhraseQuery(TITLE, QueryParser.escape(title));
        try {
            TopDocs hits = this.searcher.search(query, 1);
            return hits.scoreDocs.length != 1 ? null : hits.scoreDocs[0].doc;
        } catch (IOException e) {
            throw new PageStoreQueryException(e);
        }
    }

    /**
     * Properly shutdown the index.
     *
     * @throws IOException if closing the index fails
     */
    public void shutdown() throws IOException {

        LOGGER.debug("shutting down index writer");
        this.writer.close();
        LOGGER.debug("shutting down index directory");
        this.fsDirectory.close();

        LOGGER.info("index properly shutdown in directory={}", this.directory);
    }
}
