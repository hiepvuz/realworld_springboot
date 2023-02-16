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
    public Map<String, ArticleDTOResponse> createArticle(@RequestBody Map<String, ArticleDTOCreate> articleDTOCreateMap) {
        return articleService.create(articleDTOCreateMap);
    }

    @GetMapping("/{slug}")
    public Map<String, ArticleDTOResponse> getArticleBySlug(@PathVariable("slug") String slug) throws CustomNotFoundException {
        return articleService.getArticleBySlug(slug);
    }

    @PostMapping("/{slug}/favorite")
    public Map<String, ArticleDTOResponse> favoriteArticle(@PathVariable("slug") String slug) throws CustomNotFoundException {
        return articleService.favoriteArticle(slug);
    }

    @DeleteMapping("/{slug}/favorite")
    public Map<String, ArticleDTOResponse> unfavoriteArticle(@PathVariable("slug") String slug) throws CustomNotFoundException {
        return articleService.unfavoriteArticle(slug);
    }

    @GetMapping("")
    public Map<String, Object> getListArticle
            (@RequestParam(value = "tag", required = false) String tag,
             @RequestParam(value = "author", required = false) String author,
             @RequestParam(value = "favorite", required = false) String favorite,
             @RequestParam(value = "limit", required = false) Integer limit,
             @RequestParam(value = "offset", required = false) Integer offset) {

        return articleService.getListArticle(tag,author,favorite,limit,offset);

    }

}
