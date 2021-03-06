package com.example.es.repository;

import com.example.es.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
@Repository
public class UserRepositoryClientType {
    private final String USER_INDEX = "user_index";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    /**
     * 新增索引并创建mapping
     * @throws IOException
     */
    public void addIndex() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder()
            .startObject()
            .field("properties")
            .startObject()
            .field("userName").startObject().field("index", "true").field("type", "keyword").endObject()
            .field("age").startObject().field("index", "true").field("type", "integer").endObject()
            .field("describeMessage").startObject().field("index", "true").field("type", "text").field("analyzer", "ik_max_word").endObject()
            .field("describeMessageBack").startObject().field("index", "true").field("type", "text").endObject()
            .field("married").startObject().field("index", "false").field("type", "boolean").endObject()
            .field("birthday").startObject().field("index", "true").field("type", "date").field("format", "strict_date_optional_time||epoch_millis").endObject()
            .endObject()
            .endObject();
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(USER_INDEX);
        createIndexRequest.mapping(builder);
        CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);


        // 创建索引请求
        //CreateIndexRequest request = new CreateIndexRequest(USER_INDEX);
        // 客户端执行：创建索引的请求
        //CreateIndexResponse createIndexResponse = client.indices().create(request,  RequestOptions.DEFAULT);


        //client.close(); 是否需要关闭
    }


    public void updateIndexMapping(){

    }

    /**
     * 判断索引是否存在
     * @throws IOException
     */
    public boolean isExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest(USER_INDEX);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        return exists;
    }

    /**
     * 获取索引
     * @return
     * @throws IOException
     */
    public String getUserIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest(USER_INDEX);
        GetIndexResponse res = client.indices().get(request, RequestOptions.DEFAULT);
        return objectMapper.writeValueAsString(res.getMappings());
    }

    /**
     * 删除索引
     * @throws IOException
     */
    public void deleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(USER_INDEX);
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
    }

    /**
     * 新增一条记录
     * @param user
     * @throws IOException
     */
    public void saveUser(User user) throws IOException {
        IndexRequest request = new IndexRequest(USER_INDEX);
        request.source(objectMapper.writeValueAsString(user), XContentType.JSON);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);

        //IndexResponse[index=user_index,type=_doc,id=678YOnIBjdswrGGdxQuN,version=1,result=created,seqNo=0,primaryTerm=1,shards={"total":1,"successful":1,"failed":0}]
        System.out.println(indexResponse.toString());
    }

    /**
     * 测试是否存在记录
     * @throws IOException
     */
    public boolean userIsExists(String userId) throws IOException {
        GetRequest Request = new GetRequest(USER_INDEX, userId);
        boolean exists = client.exists(Request, RequestOptions.DEFAULT);
        return exists;
    }

    /**
     * 查询
     * @throws IOException
     */
    public User getUser(String userId) throws IOException {
        GetRequest Request = new GetRequest(USER_INDEX, userId);
        GetResponse documentFields = client.get(Request, RequestOptions.DEFAULT);

        System.out.println(documentFields); // {"_index":"person","_type":"_doc","_id":"fswRhnEBWRRkPpZFKbAZ","_version":1,"_seq_no":0,"_primary_term":1,"found":true,"_source":{"name":"谢","age":20}}
        System.out.println(documentFields.getSourceAsString()); // {"name":"谢","age":20}
        User user = objectMapper.readValue(documentFields.getSourceAsString(),User.class);
        return user;
    }

    /**
     * 更新
     * @param user
     * @throws IOException
     */
    public void updateUser(User user) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(USER_INDEX, user.getId());
        updateRequest.doc(objectMapper.writeValueAsString(user), XContentType.JSON);
        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
    }

    /**
     * 删除
     * @param userId
     * @throws IOException
     */
    public void deleteUser(String userId) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(USER_INDEX, userId);
        DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);

        System.out.println(deleteResponse);
        System.out.println(deleteResponse.status());
    }

    /**
     * 批量插入
     * @param userList
     * @throws IOException
     */
    public void bulkInsert(List<User> userList) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");

        for (int i = 0; i < userList.size(); i++) {
            bulkRequest.add(new IndexRequest(USER_INDEX).source(objectMapper.writeValueAsString(userList.get(i)), XContentType.JSON));
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.hasFailures());  // 是否失败，false代表成功
    }

    /**
     * 批量查找
     * @throws IOException
     */
    public void batchSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest(USER_INDEX);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 精确查询
        //TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("userName", "陆");
        //sourceBuilder.query(termQueryBuilder);

        // 批量匹配

        // 查询所有
        MatchAllQueryBuilder allQueryBuilder = QueryBuilders.matchAllQuery();
        sourceBuilder.query(allQueryBuilder);

        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        System.out.println(objectMapper.writeValueAsString(searchResponse.getHits()));
        System.out.println("========================");
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }

    }
}
