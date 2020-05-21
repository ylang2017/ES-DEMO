/*
package com.example.es.repository;

import com.example.es.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

*/
/**
 * User数据持久层接口
 * ElasticsearchRepository已经内置了一些列的CRUD方法，此出只写几个具有代表性的持久化操作。
 *//*

@Repository
public interface UserRepository extends ElasticsearchRepository<User, String> {
    */
/**
     * 通过名称查询，这里其实不需要写@Query注解内的查询语句，spring data已经进行了相当丰富的封装。
     * 常用的操作几乎都已涵盖在内置方法中，因此在使用时只需要参照语句与方法名关系图定义方法名即可使用对应的查询语句。
     * 但是这种方式对于学习比较不利，对于不熟悉spring data的学习者来说会一头雾水。
     *
     * 我这里在方法后缀加了self，目的就是使用自定义的注解去查询，并验证语法是否正确。如果不加则自动匹配spring data的对应语句。
     * @param userName
     * @param pageable
     * @return
     *//*

    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"userName\" : \"?\"}}}}")
    Page<User> findByUserNameSelf(String userName, Pageable pageable);

    @Query("{\"bool\" : {\"must\" : {\"range\"   {\"age\" :{\"from\":\"?\",\"to\":\"?\",\"include_lower\":true,\"include_upper\":true}}}}}")
    Page<User> findByAgeBetweenSelf(Integer ageStart,Integer ageEnd);

    */
/**
     * 下面的方法演示已方法名自动匹配spring data的查询语句
     * @param describeMessage
     * @return
     *//*

    Page<User> findByDescribeMessageContaining(String describeMessage);
    Page<User> findByDescribeMessageBackContaining(String describeMessageBack);
}
*/
