package com.buyme.admin.service.impl;

import java.util.List;

import com.buyme.admin.paging.PagingAndSortingHelper;
import com.buyme.common.entity.User;
import com.buyme.common.entity.article.Article;
import com.buyme.common.entity.article.ArticleType;
import com.buyme.common.exception.ArticleNotFoundException;

public interface IArticleService {

    public void listByPage(int pageNum, PagingAndSortingHelper helper);
    public Article get(Integer id) throws ArticleNotFoundException;
    public void save(Article article, User user);
    public void delete(Integer id) throws ArticleNotFoundException;
    public void updatePublishStatus(Integer id, boolean published) throws ArticleNotFoundException;
    public List<Article> findByTypeOrderByTitle(ArticleType type);
}

