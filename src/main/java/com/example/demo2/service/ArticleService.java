package com.example.demo2.service;

import com.example.demo2.exception.custom.CustomNotFoundException;
import com.example.demo2.model.article.dto.ArticleDTOCreate;
import com.example.demo2.model.article.dto.ArticleDTOResponse;

import java.util.Map;

public interface ArticleService {
    Map<String, ArticleDTOResponse> create(Map<String, ArticleDTOCreate> articleDTOCreateMap);

    Map<String, ArticleDTOResponse> getArticleBySlug(String slug) throws CustomNotFoundException;
}