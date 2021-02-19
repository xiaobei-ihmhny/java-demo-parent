package com.xiaobei.es.rest.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.xiaobei.es.rest.demo.domain.common.Page;
import com.xiaobei.es.rest.demo.domain.model.BookModel;
import com.xiaobei.es.rest.demo.domain.vo.BookRequestVO;
import com.xiaobei.es.rest.demo.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-19 19:53:53
 */
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private static final String INDEX_NAME = "book";
    private static final String INDEX_TYPE = "_doc";

    @Autowired
    private RestHighLevelClient client;


    @Override
    public Page<BookModel> list(BookRequestVO bookRequestVO) {
        int pageNo = bookRequestVO.getPageNo();
        int pageSize = bookRequestVO.getPageSize();

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(pageNo - 1);
        sourceBuilder.size(pageSize);
        sourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.ASC));
//        sourceBuilder.query(QueryBuilders.matchAllQuery());

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (StringUtils.isNotBlank(bookRequestVO.getName())) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("name", bookRequestVO.getName()));
        }
        if (StringUtils.isNotBlank(bookRequestVO.getAuthor())) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("author", bookRequestVO.getAuthor()));
        }
        if (null != bookRequestVO.getStatus()) {
            boolQueryBuilder.must(QueryBuilders.termQuery("status", bookRequestVO.getStatus()));
        }
        if (StringUtils.isNotBlank(bookRequestVO.getSellTime())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("sellTime", bookRequestVO.getSellTime()));
        }
        if (StringUtils.isNotBlank(bookRequestVO.getCategories())) {
            String[] categoryArr = bookRequestVO.getCategories().split(",");
            List<Integer> categoryList = Arrays.asList(categoryArr).stream().map(e->Integer.valueOf(e)).collect(Collectors.toList());
            BoolQueryBuilder categoryBoolQueryBuilder = QueryBuilders.boolQuery();
            for (Integer category : categoryList) {
                categoryBoolQueryBuilder.should(QueryBuilders.termQuery("category", category));
            }
            boolQueryBuilder.must(categoryBoolQueryBuilder);
        }

        sourceBuilder.query(boolQueryBuilder);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(INDEX_NAME);
        searchRequest.source(sourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            RestStatus restStatus = searchResponse.status();
            if (restStatus != RestStatus.OK) {
                return null;
            }

            List<BookModel> list = new ArrayList<>();
            SearchHits searchHits = searchResponse.getHits();
            for (SearchHit hit : searchHits.getHits()) {
                String source = hit.getSourceAsString();
                BookModel book = JSON.parseObject(source, BookModel.class);
                list.add(book);
            }

            long totalHits = searchHits.getTotalHits().value;

            Page<BookModel> page = new Page<>(pageNo, pageSize, totalHits, list);

            TimeValue took = searchResponse.getTook();
            log.info("查询成功！请求参数: {}, 用时{}毫秒", searchRequest.source().toString(), took.millis());

            return page;
        } catch (IOException e) {
            log.error("查询失败！原因: {}", e.getMessage(), e);
        }

        return null;
    }

    @Override
    public void save(BookModel bookModel) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("id", bookModel.getId());
        jsonMap.put("name", bookModel.getName());
        jsonMap.put("author", bookModel.getAuthor());
        jsonMap.put("category", bookModel.getCategory());
        jsonMap.put("price", bookModel.getPrice());
        jsonMap.put("sellTime", bookModel.getSellTime());
        jsonMap.put("sellReason", bookModel.getSellReason());
        jsonMap.put("status", bookModel.getStatus());

        IndexRequest indexRequest = new IndexRequest(INDEX_NAME, INDEX_TYPE, String.valueOf(bookModel.getId()));
        indexRequest.source(jsonMap);

        client.indexAsync(indexRequest, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                String index = indexResponse.getIndex();
                String type = indexResponse.getType();
                String id = indexResponse.getId();
                long version = indexResponse.getVersion();

//                log.info("Index: {}, Type: {}, Id: {}, Version: {}", index, type, id, version);

                if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
                    log.info("写入文档");
                } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                    log.info("修改文档");
                }
                ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
                if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                    log.warn("部分分片写入成功");
                }
                if (shardInfo.getFailed() > 0) {
                    for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                        String reason = failure.reason();
                        log.warn("失败原因: {}", reason);
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                log.error(e.getMessage(), e);
            }
        });
    }

    @Override
    public void update(BookModel bookModel) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("sellReason", bookModel.getSellReason());
        UpdateRequest request = new UpdateRequest(INDEX_NAME, INDEX_TYPE, String.valueOf(bookModel.getId()));
        request.doc(jsonMap);
        try {
            UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("更新失败！原因: {}", e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        DeleteRequest request = new DeleteRequest(INDEX_NAME, INDEX_TYPE, String.valueOf(id));
        try {
            DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
            if (deleteResponse.status() == RestStatus.OK) {
                log.info("删除成功！id: {}", id);
            }
        } catch (IOException e) {
            log.error("删除失败！原因: {}", e.getMessage(), e);
        }
    }

    @Override
    public BookModel detail(int id) {
        GetRequest getRequest = new GetRequest(INDEX_NAME, INDEX_TYPE, String.valueOf(id));
        try {
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            if (getResponse.isExists()) {
                String source = getResponse.getSourceAsString();
                BookModel book = JSON.parseObject(source, BookModel.class);
                return book;
            }
        } catch (IOException e) {
            log.error("查看失败！原因: {}", e.getMessage(), e);
        }
        return null;
    }
}
