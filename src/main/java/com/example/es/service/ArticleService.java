package com.example.es.service;

import com.example.es.entity.Article;
import com.example.es.repository.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    /**
     * 批量新增
     *
     * @param articleList
     */
    public void bulkAdd(List<Article> articleList) {
        articleMapper.saveAll(articleList);
    }

    /**
     * 通过id删除
     *
     * @return
     */
    public void deleteArticleById(String articleId) {
        articleMapper.deleteById(articleId);
    }

    /**
     * 全文检索
     *
     * @param contentMatch
     * @return
     */
    public List<Article> getArticleListByMatch(String contentMatch, Pageable pageable) {
        return articleMapper.findByArticleContentContaining(contentMatch, pageable);
    }

    /**
     * 按作者查找
     *
     * @param authorName
     * @return
     */
    public Page<Article> getArticleListByAuthor(String authorName, Pageable pageable) {
        return articleMapper.findByArticleAuthor(authorName, pageable);
    }

    public Page<Article> getArticleListByAuthorSelf(String authorName, Pageable pageable) {
        return articleMapper.findByArticleAuthorSelf(authorName, pageable);
    }

    /**
     * 查找指定时间发布的文章
     *
     * @param beforeDate
     * @param afterDate
     * @return
     */
    public Page<Article> getArticleListByDate(Date beforeDate, Date afterDate, Pageable pageable) {
        return articleMapper.findByPublishDateBetween(afterDate, beforeDate, pageable);
    }

    /**
     * 按标题检索
     * @param title
     * @param pageable
     * @return
     */
    public Page<Article> getArticleListByTitle(String title, Pageable pageable) {
        return articleMapper.findByArticleTitle(title, pageable);
    }
}
