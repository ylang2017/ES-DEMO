package com.example.es.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "article_index",shards = 5, replicas = 1)// 测试建立5个分片，1个副本
public class Article {
    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String articleTitle;

    @Field(type = FieldType.Keyword)
    private String articleAuthor;

    @Field(type = FieldType.Integer)
    private Integer characterCount;

    @Field(type = FieldType.Double)
    private Double cost;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String articleContent;

    @Field(type = FieldType.Text)
    private String articleContentBack;

    @Field(type = FieldType.Date)
    private Date publishDate;

    @Field(type = FieldType.Boolean)
    private Boolean available;
}
