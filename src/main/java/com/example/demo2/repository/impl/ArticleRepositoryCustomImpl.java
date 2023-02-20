package com.example.demo2.repository.impl;

import com.example.demo2.entity.Article;
import com.example.demo2.repository.ArticleRepositoryCustom;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Map<String, Object> getListArticle(String tag, String author, String favorite,
                                              Integer limit, Integer offset) {
        StringBuilder query = new StringBuilder("select DISTINCT a from Article a left join a.author au " +
                " left join a.tagList atl left join a.usersFavorite auf where 1=1");

        Map<String, Object> params = new HashMap<>();

        if (!ObjectUtils.isEmpty(tag)) {
            query.append(" and atl.tag like :tag");
            params.put("tag", "%" + tag + "%");
        }

        if (!ObjectUtils.isEmpty(author)) {
            query.append(" and au.username = :author");
            params.put("author", author);
        }
        if (!ObjectUtils.isEmpty(favorite)) {
            query.append(" and auf.username = :favorite");
            params.put("favorite", favorite);
        }

        TypedQuery<Article> typedQuery = entityManager.createQuery(query.toString(), Article.class);
        Query countQuery = entityManager.createQuery(query.toString()
                .replace("select DISTINCT a", "select count(DISTINCT a.id)"));
        String cq = query.toString().replace("select a", "select count(a.id)");
        System.out.println("query: " + query);
        System.out.println("countQuery: " + cq);


        params.forEach((k, v) -> {
            typedQuery.setParameter(k, v);
            countQuery.setParameter(k, v);
        });


        if (!ObjectUtils.isEmpty(offset)) {
            typedQuery.setFirstResult(offset);
        }

        if (!ObjectUtils.isEmpty(limit)) {
            typedQuery.setMaxResults(limit);
        }

        long totalArticle = (long) countQuery.getSingleResult();
        List<Article> listArticles = typedQuery.getResultList();

        Map<String, Object> resultWrapper = new HashMap<>();
        resultWrapper.put("listArticles", listArticles);
        resultWrapper.put("totalArticle", totalArticle);

        return resultWrapper;
    }
}
