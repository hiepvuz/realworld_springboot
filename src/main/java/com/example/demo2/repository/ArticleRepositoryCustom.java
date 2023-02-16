package com.example.demo2.repository;



import java.util.Map;

public interface ArticleRepositoryCustom {
    Map<String, Object> getListArticle(String tag, String author, String favorite, Integer limit, Integer offset);
}
