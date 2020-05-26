package com.example.es.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * 声明一个实体类，实体类属性覆盖常用的数据类型用于测试
 */
@Data
@NoArgsConstructor
@Document(indexName = "user_index", shards = 1, replicas = 0)// shards分区数，由于单机测试就只写1，replicas每个分区的备份数。
public class User {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String userName;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")// 这里使用ik分词器，对此字段进行最大程度分词，尽可能多的匹配分词。与之相对的还有ik_smart，粗粒度分词。
    private String describeMessage;

    @Field(type = FieldType.Text)// 这里设置一个和describeMessage相同的属性，但是不使用分词器，用以比较二者对关键词命中的差别。
    private String describeMessageBack;

    @Field(type = FieldType.Date)
    private Date birthDay;

    @Field(type = FieldType.Boolean)
    private Boolean married;

    public User(String id, String userName, Integer age, String describeMessage, String describeMessageBack, Date birthDay, Boolean married) {
        this.id = id;
        this.userName = userName;
        this.age = age;
        this.describeMessage = describeMessage;
        this.describeMessageBack = describeMessageBack;
        this.birthDay = birthDay;
        this.married = married;
    }
}
