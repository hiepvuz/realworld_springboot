package com.example.demo2.controller;


import com.example.demo2.entity.Article;
import com.example.demo2.exception.custom.CustomNotFoundException;
import com.example.demo2.model.article.dto.ArticleDTOCreate;
import com.example.demo2.model.article.dto.ArticleDTOResponse;
import com.example.demo2.model.article.dto.ArticleDTOUpdate;
import com.example.demo2.model.comment.dto.CommentDTOCreate;
import com.example.demo2.model.comment.dto.CommentDTOResponse;
import com.example.demo2.repository.ArticleRepository;
import com.example.demo2.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    @PostMapping("")
    public Map<String, ArticleDTOResponse> createArticle
            (@RequestBody Map<String, ArticleDTOCreate> articleDTOCreateMap) throws CustomNotFoundException {
        return articleService.create(articleDTOCreateMap);
    }

    @PutMapping("/{slug}")
    public Map<String, Article> updateArticle
            (@PathVariable("slug") String slug, @RequestBody Map<String, ArticleDTOUpdate> articleDTOUpdateMap)
            throws CustomNotFoundException {
        return articleService.update(slug, articleDTOUpdateMap);
    }

    @GetMapping("/{slug}")
    public Map<String, ArticleDTOResponse> getArticleBySlug(@PathVariable("slug") String slug)
            throws CustomNotFoundException {
        return articleService.getArticleBySlug(slug);
    }

    @PostMapping("/{slug}/favorite")
    public Map<String, ArticleDTOResponse> favoriteArticle(@PathVariable("slug") String slug)
            throws CustomNotFoundException {
        return articleService.favoriteArticle(slug);
    }

    @DeleteMapping("/{slug}/favorite")
    public Map<String, ArticleDTOResponse> unfavoriteArticle(@PathVariable("slug") String slug)
            throws CustomNotFoundException {
        return articleService.unfavoriteArticle(slug);
    }

    @GetMapping("")
    public Map<String, Object> getListArticle
            (@RequestParam(value = "tag", required = false) String tag,
             @RequestParam(value = "author", required = false) String author,
             @RequestParam(value = "favorite", required = false) String favorite,
             @RequestParam(value = "limit", defaultValue = "20") Integer limit,
             @RequestParam(value = "offset", defaultValue = "0") Integer offset) {

        return articleService.getListArticle(tag, author, favorite, limit, offset);
    }

    @GetMapping("/feed")
    public Map<String, Object> getFeed() {
        return articleService.getFeed();
    }
//
//    @PostMapping("/{slug}/comments")
//    public Map<String, CommentDTOResponse> addComment
//            (@PathVariable String slug,
//             @RequestBody Map<String, CommentDTOCreate> commentDTOCreateMap) throws CustomNotFoundException {
//        return articleService.addComment(slug, commentDTOCreateMap);
//    }

    @GetMapping("/{slug}/comments")
    public Map<String, Object> getComment (@PathVariable String slug) throws CustomNotFoundException {
        return articleService.getComment(slug);
    }
}
