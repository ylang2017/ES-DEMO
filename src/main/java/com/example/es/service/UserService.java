package com.example.es.service;

import com.example.es.entity.User;
//import com.example.es.repository.UserRepository;
<<<<<<< HEAD
import com.example.es.repository.UserRepositoryClientType;
=======
>>>>>>> 30270083436a9d909ab159b2324f16a352c83d45
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
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {
<<<<<<< HEAD
    // 此类已废弃
    /* @Autowired
=======
    private final String USER_INDEX = "user_index";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;
    // 此类已废弃
   /* @Autowired
>>>>>>> 30270083436a9d909ab159b2324f16a352c83d45
    private ElasticsearchRestTemplate elasticsearchTemplate;*/
    /*@Autowired
    UserRepository userRepository;*/

<<<<<<< HEAD
    @Autowired
    UserRepositoryClientType userRepository;

=======
>>>>>>> 30270083436a9d909ab159b2324f16a352c83d45
    /**
     * 新增索引
     * @throws IOException
     */
<<<<<<< HEAD
    public void addUserIndex() throws IOException {
        userRepository.addIndex();
=======
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
>>>>>>> 30270083436a9d909ab159b2324f16a352c83d45
    }

    /**
     * 判断索引是否存在
     * @throws IOException
     */
<<<<<<< HEAD
    public boolean isExistUserIndex() throws IOException {
        return userRepository.isExistIndex();
=======
    public boolean isExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest(USER_INDEX);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        return exists;
>>>>>>> 30270083436a9d909ab159b2324f16a352c83d45
    }

    /**
     * 获取索引
     * @return
     * @throws IOException
     */
    public String getUserIndex() throws IOException {
<<<<<<< HEAD
        return userRepository.getUserIndex();
=======
        GetIndexRequest request = new GetIndexRequest(USER_INDEX);
        GetIndexResponse res = client.indices().get(request, RequestOptions.DEFAULT);
        return objectMapper.writeValueAsString(res.getMappings());
>>>>>>> 30270083436a9d909ab159b2324f16a352c83d45
    }

    /**
     * 删除索引
     * @throws IOException
     */
    public void deleteIndex() throws IOException {
<<<<<<< HEAD
        userRepository.deleteIndex();
=======
        DeleteIndexRequest request = new DeleteIndexRequest(USER_INDEX);
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
>>>>>>> 30270083436a9d909ab159b2324f16a352c83d45
    }

    /**
     * 新增一条记录
     * @param user
     * @throws IOException
     */
    public void saveUser(User user) throws IOException {
<<<<<<< HEAD
        userRepository.saveUser(user);
=======
        IndexRequest request = new IndexRequest("person");
        request.source(objectMapper.writeValueAsString(user), XContentType.JSON);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
>>>>>>> 30270083436a9d909ab159b2324f16a352c83d45
    }

    /**
     * 测试是否存在记录
     * @throws IOException
     */
   public boolean userIsExists(String userId) throws IOException {
<<<<<<< HEAD
        return userRepository.userIsExists(userId);
=======
        GetRequest Request = new GetRequest(USER_INDEX, userId);
        boolean exists = client.exists(Request, RequestOptions.DEFAULT);
        return exists;
>>>>>>> 30270083436a9d909ab159b2324f16a352c83d45
    }

    /**
     * 查询
     * @throws IOException
     */
   public User getUser(String userId) throws IOException {
<<<<<<< HEAD
       return userRepository.getUser(userId);
    }

    public void updateUser(User user) throws IOException {
        userRepository.updateUser(user);
    }

    public void deleteUser(String userId) throws IOException {
        userRepository.deleteUser(userId);
=======
        GetRequest Request = new GetRequest(USER_INDEX, userId);
        GetResponse documentFields = client.get(Request, RequestOptions.DEFAULT);

        System.out.println(documentFields); // {"_index":"person","_type":"_doc","_id":"fswRhnEBWRRkPpZFKbAZ","_version":1,"_seq_no":0,"_primary_term":1,"found":true,"_source":{"name":"谢","age":20}}
        System.out.println(documentFields.getSourceAsString()); // {"name":"谢","age":20}
       return null;
    }

    public void updateUser(User user) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(USER_INDEX, user.getId());
        updateRequest.doc(objectMapper.writeValueAsString(user), XContentType.JSON);
        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
    }

    public void deleteUser(String userId) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(USER_INDEX, userId);
        DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);

        System.out.println(deleteResponse);
        System.out.println(deleteResponse.status());
>>>>>>> 30270083436a9d909ab159b2324f16a352c83d45
    }

    /**
     * 批量插入
     * @param userList
     * @throws IOException
     */
   public void bulkInsert(List<User> userList) throws IOException {
<<<<<<< HEAD
       userRepository.bulkInsert(userList);
    }

    public void batchSearch() throws IOException {
        userRepository.batchSearch();
=======
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");

        for (int i = 0; i < userList.size(); i++) {
            bulkRequest.add(new IndexRequest(USER_INDEX).source(objectMapper.writeValueAsString(userList.get(i)), XContentType.JSON));
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.hasFailures());  // 是否失败，false代表成功
    }

    public void batchSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest(USER_INDEX);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 精确查询
        //TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("userName", "陆");
        //sourceBuilder.query(termQueryBuilder);

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

>>>>>>> 30270083436a9d909ab159b2324f16a352c83d45
    }
}
