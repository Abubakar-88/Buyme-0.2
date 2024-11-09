package com.buyme.admin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.buyme.admin.paging.SearchRepository;
import com.buyme.common.entity.article.Article;
import com.buyme.common.entity.article.ArticleType;

public interface ArticleRepository extends SearchRepository<Article, Integer> {

    @Query("SELECT NEW Article(a.id, a.title, a.type, a.updatedTime, a.published, a.user) "
            + "FROM Article a")
    public Page<Article> findAll(Pageable pageable);

    @Query("SELECT NEW Article(a.id, a.title, a.type, a.updatedTime, a.published, a.user) "
            + "FROM Article a WHERE a.title LIKE %?1% OR a.content LIKE %?1%")
    public Page<Article> findAll(String keyword, Pageable pageable);

    @Query("UPDATE Article a SET a.published = ?2 WHERE a.id = ?1")
    @Modifying
    public void updatePublishStatus(Integer id, boolean published);

    public List<Article> findByTypeOrderByTitle(ArticleType type);

    @Query("SELECT NEW Article(a.id, a.title) From Article a WHERE a.published = true ORDER BY a.title")
    public List<Article> findPublishedArticlesWithIDAndTitleOnly();

    @Query("SELECT a.id, COUNT(m.id) FROM Menu m JOIN m.article a GROUP BY a.id")
    List<Object[]> countArticlesByMenu();


    @Query("SELECT COUNT(a.id) FROM Article a WHERE a.type = 0")
     Long boundMenuCount();

    @Query("SELECT COUNT(a.id) FROM Article a WHERE a.type = 1")
    Long freeArticleCount();

    @Query("SELECT COUNT(a.id) FROM Article a WHERE a.published = 1")
    Long publishedArticleCount();

    @Query("SELECT COUNT(a.id) FROM Article a WHERE a.published = 0")
    Long unpublishedArticleCount();
}
