package com.example.demo2.service;

import com.example.demo2.entity.Article;
import com.example.demo2.exception.custom.CustomNotFoundException;
import com.example.demo2.model.article.dto.ArticleDTOCreate;
import com.example.demo2.model.article.dto.ArticleDTOResponse;
import com.example.demo2.model.article.dto.ArticleDTOUpdate;

import java.util.Map;

public interface ArticleService {
    Map<String, ArticleDTOResponse> create(Map<String, ArticleDTOCreate> articleDTOCreateMap);

    Map<String, ArticleDTOResponse> getArticleBySlug(String slug) throws CustomNotFoundException;

    Map<String, ArticleDTOResponse> favoriteArticle(String slug) throws CustomNotFoundException;

    Map<String, ArticleDTOResponse> unfavoriteArticle(String slug) throws CustomNotFoundException;

    Map<String, Object> getListArticle(String tag, String author, String favorite, Integer limit, Integer offset);

    Map<String, Article> update(String slug, Map<String, ArticleDTOUpdate> articleDTOUpdateMap) throws CustomNotFoundException;

//    Map<String, Object> getFeed(String limit, String offset);

    Map<String, Object> getFeed();
}
