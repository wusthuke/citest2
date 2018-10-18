/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.huke.demo.simpleboot.es;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * @author huke
 * @version $Id: EsTest.java, v 0.1 2018年10月18日 下午3:16 huke Exp $
 */
public class EsTest {

    private RestHighLevelClient client;

    private AtomicLong atomicInteger;

    private String index = "huke";

    @Before
    public void before() throws IOException {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("linkc", "G3Za3MYF"));

        // search.alipay.net
        RestClientBuilder builder = RestClient.builder(new HttpHost("search.alipay.net", 9200))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        client = new RestHighLevelClient(builder);

        Optional<Long> max = this.testSearchMaxId();
        atomicInteger = new AtomicLong(max.orElse(0L));
    }

    @After
    public void after() throws IOException {
        client.close();
    }

    public Optional<Long> testSearchMaxId() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(this.index);

        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource()
                .query(QueryBuilders.matchAllQuery())
                .fetchSource(new String[] {"id", "title"}, new String[] {})
                .sort(new FieldSortBuilder("id").order(SortOrder.DESC))
                .size(1);

        searchRequest.source(searchSourceBuilder);

        SearchResponse response = this.client.search(searchRequest, RequestOptions.DEFAULT);
        assertTrue(response.status() == RestStatus.OK);
        if (response.getHits().getHits().length == 1) {
            Long max = Long.valueOf(String.valueOf(response.getHits().getHits()[0].getSourceAsMap().get("id")));
            return Optional.of(max);
        }
        return Optional.empty();
    }

    @Test
    public void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(this.index);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = this.client.search(searchRequest, RequestOptions.DEFAULT);
        assertTrue(response.status() == RestStatus.OK);
    }

    @Test
    public void testInsert() throws Exception {
        boolean result = this.insertMockPullRequest();
        assertSame(Boolean.TRUE, result);
    }

    @Test
    public void testTermsQueryForProject() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(this.index);

        int start = new Random().nextInt(100000);
        List<Long> projectIds = LongStream.range(start, start + 20000).boxed().collect(Collectors.toList());
        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource()
                .query(
                        QueryBuilders.boolQuery().must(this.generatorTermsBoolQuery("project_id", projectIds))
                ).fetchSource(new String[] {"id", "title"}, new String[] {})
                .sort(new FieldSortBuilder("id").order(SortOrder.DESC))
                .size(20);

        searchRequest.source(searchSourceBuilder);

        SearchResponse response = this.client.search(searchRequest, RequestOptions.DEFAULT);
        assertTrue(response.status() == RestStatus.OK);
        System.out.println("start no: " + start + ";" +  response.getTook() + "; " + response.getHits().getTotalHits() + " hits;");
    }

    private <T> Collection<List<T>> partition(List<T> list, int size) {
        final AtomicInteger counter = new AtomicInteger(0);

        return list.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / size))
                .values();
    }

    private BoolQueryBuilder generatorTermsBoolQuery(String fieldName, List items) {
        Collection<List> spiltItems = partition(items, 4096);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        spiltItems.stream().forEach(p -> {
            boolQueryBuilder.should(QueryBuilders.termsQuery(fieldName, p));
        });
        if (spiltItems.size() > 1) {
            boolQueryBuilder.minimumShouldMatch(1);
        }
        return boolQueryBuilder;
    }

    @Test
    public void testBatchInsert() throws IOException {
        ExecutorService executors = Executors.newFixedThreadPool(5);
        IntStream.range(0, 10000000).forEach(p -> {
            try {
                this.insertMockPullRequest();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (p % 2000 == 0) {
                try {
                    TimeUnit.MICROSECONDS.sleep(100);
                } catch (InterruptedException e) {
                }
            }

            /*executors.submit(() -> {
                try {
                    this.insertMockPullRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            if (p % 200 == 0) {
                try {
                    TimeUnit.MICROSECONDS.sleep(200);
                } catch (InterruptedException e) {
                }
            }*/
        });
    }

    public boolean insertMockPullRequest() throws IOException {
        this.atomicInteger.incrementAndGet();
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("id", this.atomicInteger.get());
            builder.timeField("create_at", new Date());
            builder.field("title", "trying out Elasticsearch");
            builder.field("project_id", new Random().nextInt(1000000));
        }
        builder.endObject();
        IndexRequest indexRequest = new IndexRequest(this.index, "doc", String.valueOf(atomicInteger.get()))
                .source(builder);
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        if (response.status() == RestStatus.CREATED) {
            return true;
        }

        return false;
    }
}