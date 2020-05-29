package com.example.es.repository;

import com.example.es.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ArticleMapper extends ElasticsearchRepository<Article, String> {
    List<Article> findByArticleContentContaining(String content, Pageable pageable);
    Page<Article> findByArticleAuthor(String authorName, Pageable pageable);
    @Query("{\"bool\" : {\"must\" : {\"term\" : {\"articleUser\" : \"?\"}}}}")
    Page<Article> findByArticleAuthorSelf(String authorName, Pageable pageable);
    Page<Article> findByArticleTitle(String articleTitle, Pageable pageable);
    Page<Article> findByPublishDateBetween(Date startDate, Date endDate, Pageable pageable);
}
