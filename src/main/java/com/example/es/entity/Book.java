package com.example.es.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;*/

import java.util.Date;

@Data
@NoArgsConstructor
//@AllArgsConstructor
//@Document(indexName = "book_index",shards = 1, replicas = 0)// shards索引分片数，由于单机测试就只写1，replicas每个分区的备份数。
//@Mapping(mappingPath = "/mapping/book.json")// 由于@Field无法识别设置的分词器（有对应的设置项但不会生效），独立设置mapping,版本兼容好多问题。
public class Book {
    //@Id
    private String id;

    //@Field(type = FieldType.Text)
    private String bookName;

    //@Field(type = FieldType.Keyword)
    private String author;

    //@Field(type = FieldType.Double)
    private Double cost;

    //@Field(type = FieldType.Text, analyzer = "ik_max_word")// 这里使用ik分词器，对此字段进行最大程度分词，尽可能多的匹配分词。与之相对的还有ik_smart，粗粒度分词。
    //analyzer和search_analyzer
    //索引在插入和查找时都会对分词进行一次倒排，analyzer用于设置在更新索引是是否要按特定分词器分词方式对文档进行一次分词，search_analyzer用于设置在查找时使用哪种分词方式
    //@Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String describeMessage;

    //@Field(type = FieldType.Text,analyzer = "",searchAnalyzer = "")// 这里设置一个和describeMessage相同的属性，但是不使用分词器，用以比较二者对关键词命中的差别。
    //@Field(type = FieldType.Text)
    private String describeMessageBack;

    //@Field(type = FieldType.Date)
    private Date publishDate;

    //@Field(type = FieldType.Boolean)
    private Boolean available;

    public Book(String id, String bookName, String author, Double cost, String describeMessage, String describeMessageBack, Date publishDate, Boolean available) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.cost = cost;
        this.describeMessage = describeMessage;
        this.describeMessageBack = describeMessageBack;
        this.publishDate = publishDate;
        this.available = available;
    }
}
