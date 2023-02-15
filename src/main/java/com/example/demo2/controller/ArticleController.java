package com.example.demo2.controller;


import com.example.demo2.exception.custom.CustomNotFoundException;
import com.example.demo2.model.article.dto.ArticleDTOCreate;
import com.example.demo2.model.article.dto.ArticleDTOResponse;
import com.example.demo2.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("")
    public Map<String, ArticleDTOResponse> createArticle(@RequestBody Map<String,ArticleDTOCreate> articleDTOCreateMap){
        return articleService.create(articleDTOCreateMap);
    }

    @GetMapping("/{slug}")
    public Map<String, ArticleDTOResponse> getArticleBySlug(@PathVariable("slug") String slug) throws CustomNotFoundException {
        return articleService.getArticleBySlug(slug);
    }

}
