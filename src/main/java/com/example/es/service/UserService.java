package com.example.es.service;

import com.example.es.entity.User;
import com.example.es.repository.UserRepository;
import com.example.es.repository.UserRepositoryClientType;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {
    // 此类已废弃
    /* @Autowired
    private final String USER_INDEX = "user_index"*/;

    @Autowired
    UserRepositoryClientType userRepository;

    /**
     * 新增索引
     * @throws IOException
     */
    public void addUserIndex() throws IOException {

        userRepository.addIndex();
    }


    /**
     * 判断索引是否存在
     * @throws IOException
     */
    public boolean isExistUserIndex() throws IOException {
        return userRepository.isExistIndex();
    }

    /**
     * 获取索引
     * @return
     * @throws IOException
     */
    public String getUserIndex() throws IOException {
        return userRepository.getUserIndex();
    }

    /**
     * 删除索引
     * @throws IOException
     */
    public void deleteIndex() throws IOException {
        userRepository.deleteIndex();
    }

    /**
     * 新增一条记录
     * @param user
     * @throws IOException
     */
    public void saveUser(User user) throws IOException {
        userRepository.saveUser(user);
    }

    /**
     * 测试是否存在记录
     * @throws IOException
     */
   public boolean userIsExists(String userId) throws IOException {
        return userRepository.userIsExists(userId);
    }

    /**
     * 查询
     * @throws IOException
     */
   public User getUser(String userId) throws IOException {
       return userRepository.getUser(userId);
    }

    public void updateUser(User user) throws IOException {
        userRepository.updateUser(user);
    }

    public void deleteUser(String userId) throws IOException {
        userRepository.deleteUser(userId);
    }

    /**
     * 批量插入
     * @param userList
     * @throws IOException
     */
   public void bulkInsert(List<User> userList) throws IOException {
       userRepository.bulkInsert(userList);
    }

    public void batchSearch() throws IOException {
        userRepository.batchSearch();
    }

}
