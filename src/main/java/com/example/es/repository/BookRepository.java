package com.example.es.repository;

import com.example.es.entity.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class BookRepository {
    private final String BOOK_INDEX_NAME ="book_index";
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    public void addIndex() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder()
            .startObject()
            .field("properties")
            .startObject()
            .field("bookName").startObject().field("index", "true").field("type", "text").endObject()
            .field("cost").startObject().field("index", "false").field("type", "double").endObject()
            .field("author").startObject().field("index", "true").field("type", "keyword").endObject()
            .field("describeMessage").startObject().field("index", "true").field("type", "text").field("analyzer", "ik_max_word").endObject()
            .field("describeMessageBack").startObject().field("index", "true").field("type", "text").endObject()
            .field("married").startObject().field("index", "false").field("type", "boolean").endObject()
            .field("birthday").startObject().field("index", "false").field("type", "date").field("format", "strict_date_optional_time||epoch_millis").endObject()
            .endObject()
            .endObject();
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(BOOK_INDEX_NAME);
        createIndexRequest.mapping(builder);
        CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);


        // 创建索引请求
        //CreateIndexRequest request = new CreateIndexRequest(USER_INDEX);
        // 客户端执行：创建索引的请求
        //CreateIndexResponse createIndexResponse = client.indices().create(request,  RequestOptions.DEFAULT);


        //client.close(); 是否需要关闭
    }

    public void bulkInsert(List<Book> insertList) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");

        for (int i = 0; i < insertList.size(); i++) {
            bulkRequest.add(new IndexRequest(BOOK_INDEX_NAME).source(objectMapper.writeValueAsString(insertList.get(i)), XContentType.JSON));
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println("批量操作结果："+bulkResponse.hasFailures());  // 是否存在失败，false代表没有操作失败的，即全部成功
    }

}
