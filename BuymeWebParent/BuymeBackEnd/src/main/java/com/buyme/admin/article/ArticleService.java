package com.buyme.admin.article;

import java.util.*;

import javax.transaction.Transactional;
import com.buyme.admin.service.impl.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyme.admin.paging.PagingAndSortingHelper;
import com.buyme.admin.repository.ArticleRepository;
import com.buyme.common.entity.User;
import com.buyme.common.entity.article.Article;
import com.buyme.common.entity.article.ArticleType;
import com.buyme.common.exception.ArticleNotFoundException;

@Service
@Transactional
public class ArticleService implements IArticleService {

    public static final int ARTICLES_PER_PAGE = 5;

    @Autowired
    private ArticleRepository repo;

    @Override
    public void listByPage(int pageNum, PagingAndSortingHelper helper) {
        // TODO Auto-generated method stub
        helper.listEntities(pageNum, ARTICLES_PER_PAGE, repo);
    }

    @Override
    public Article get(Integer id) throws ArticleNotFoundException {
        // TODO Auto-generated method stub
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new ArticleNotFoundException("Could not find any article with ID " + id);
        }
    }

    @Override
    public void save(Article article, User user) {
        // TODO Auto-generated method stub

        if (article.getAlias() == null || article.getAlias().isEmpty()) {
            article.setAlias(article.getTitle().replaceAll(" ", "-"));
        }

        article.setUpdatedTime(new Date());
        article.setUser(user);

        repo.save(article);
    }

    @Override
    public void delete(Integer id) throws ArticleNotFoundException {
        // TODO Auto-generated method stub
        if (!repo.existsById(id)) {
            throw new ArticleNotFoundException("Could not find any article with ID " + id);
        }
        repo.deleteById(id);
    }

    @Override
    public void updatePublishStatus(Integer id, boolean published) throws ArticleNotFoundException {
        // TODO Auto-generated method stub
        if (!repo.existsById(id)) {
            throw new ArticleNotFoundException("Could not find any article with ID " + id);
        }
        repo.updatePublishStatus(id, published);
    }

    @Override
    public List<Article> findByTypeOrderByTitle(ArticleType type) {
        // TODO Auto-generated method stub
        return repo.findByTypeOrderByTitle(ArticleType.MENU_BOUND);
    }

    public List<Article> listArticlesForMenu() {
        return repo.findByTypeOrderByTitle(ArticleType.MENU_BOUND);
    }

    public List<Article> listAll() {
        return repo.findPublishedArticlesWithIDAndTitleOnly();
    }

    public Long totalArticle(){
        return repo.count();
    }

    public List<Map<String, Object>> getArticleCountsByMenu() {
        List<Object[]> results = repo.countArticlesByMenu();
        List<Map<String, Object>> counts = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> count = new HashMap<>();
            count.put("Menu", row[0]);

            counts.add(count);
        }
        return counts;
    }

}
