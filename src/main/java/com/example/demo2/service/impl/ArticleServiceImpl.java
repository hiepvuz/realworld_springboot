package com.example.demo2.service.impl;

import com.example.demo2.entity.Article;
import com.example.demo2.entity.Tag;
import com.example.demo2.entity.User;
import com.example.demo2.exception.custom.CustomNotFoundException;
import com.example.demo2.model.CustomError;
import com.example.demo2.model.article.dto.ArticleDTOCreate;
import com.example.demo2.model.article.dto.ArticleDTOResponse;
import com.example.demo2.model.article.mapper.ArticleMapper;
import com.example.demo2.repository.ArticleRepository;
import com.example.demo2.repository.TagRepository;
import com.example.demo2.service.ArticleService;
import com.example.demo2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final TagRepository tagRepository;

    @Override
    public Map<String, ArticleDTOResponse> create(Map<String, ArticleDTOCreate> articleDTOCreateMap) {
        ArticleDTOCreate articleDTOCreate = articleDTOCreateMap.get("article");
        List<Tag> tagList = tagRepository.findByIdIn(articleDTOCreate.getTagList());
        System.out.println(tagList.size());
        Set<Tag> tagSet = new HashSet<>(tagList);
        Article article = ArticleMapper.toArticle(articleDTOCreate);
        article.setTagList(tagSet);
        article.setUsersFavorite(new HashSet<>());
        article.setAuthor(userService.getLoggedInUser());
        article = articleRepository.save(article);
        Map<String, ArticleDTOResponse> wrapper = new HashMap<>();
        wrapper.put("article", ArticleMapper.toArticleDTOResponse(article, false, false));
        return wrapper;
    }

    @Override
    public Map<String, ArticleDTOResponse> getArticleBySlug(String slug) throws CustomNotFoundException {
        Optional<Article> optionalArticle = articleRepository.findArticleBySlug(slug);
        User loggedInUser = userService.getLoggedInUser();
        if (!optionalArticle.isPresent()) {
            throw new CustomNotFoundException(CustomError.builder().code("404").message("Not found article").build());
        }
        Article article = optionalArticle.get();
        User userLoggedIn = userService.getLoggedInUser();
        User user = optionalArticle.get().getAuthor();
        boolean isFollowing = false;
        for (User u : user.getFollowers()) {
            if (u.getId() == (userLoggedIn.getId())) {
                isFollowing = true;
                break;
            }
        }
        boolean isFavorite = false;
        Set<User> usersFavorite = article.getUsersFavorite();
        for (User u : usersFavorite) {
            if (u.getId() == loggedInUser.getId()) {
                isFavorite = true;
                break;
            }
        }
        ArticleDTOResponse articleDTOResponse = ArticleMapper.toArticleDTOResponse(article,
                isFavorite, isFollowing);

        Map<String, ArticleDTOResponse> wrapper = new HashMap<>();
        wrapper.put("article", articleDTOResponse);

        return wrapper;
    }

    @Override
    public Map<String, ArticleDTOResponse> favoriteArticle(String slug) throws CustomNotFoundException {
        User loggedInUser = userService.getLoggedInUser();
        Optional<Article> optionalArticle = articleRepository.findArticleBySlug(slug);
        if (!optionalArticle.isPresent()) {
            throw new CustomNotFoundException(CustomError.builder().code("404").message("Article not found").build());
        }
        Article article = optionalArticle.get();
        Set<User> favoritesUser = article.getUsersFavorite();
        favoritesUser.add(loggedInUser);
        User user = article.getAuthor();
        article = articleRepository.save(article);
        boolean isFollowing = false;
        for (User u : user.getFollowers()) {
            if (u.getId() == (loggedInUser.getId())) {
                isFollowing = true;
                break;
            }
        }
        ArticleDTOResponse articleDTOResponse = ArticleMapper.toArticleDTOResponse(article, true, isFollowing);
        Map<String, ArticleDTOResponse> wrapper = new HashMap<>();
        wrapper.put("article", articleDTOResponse);
        return wrapper;
    }

    @Override
    public Map<String, ArticleDTOResponse> unfavoriteArticle(String slug) throws CustomNotFoundException {
        User loggedInUser = userService.getLoggedInUser();
        Optional<Article> optionalArticle = articleRepository.findArticleBySlug(slug);
        if (!optionalArticle.isPresent()) {
            throw new CustomNotFoundException(CustomError.builder().code("404").message("Article not found").build());
        }
        Article article = optionalArticle.get();
        Set<User> favoritesUser = article.getUsersFavorite();
        favoritesUser.remove(loggedInUser);
        User user = article.getAuthor();
        article = articleRepository.save(article);
        boolean isFollowing = false;
        for (User u : user.getFollowers()) {
            if (u.getId() == (loggedInUser.getId())) {
                isFollowing = true;
                break;
            }
        }
        ArticleDTOResponse articleDTOResponse = ArticleMapper.toArticleDTOResponse(article, false, isFollowing);
        Map<String, ArticleDTOResponse> wrapper = new HashMap<>();
        wrapper.put("article", articleDTOResponse);
        return wrapper;
    }

    @Override
    public Map<String, Object> getListArticle(String tag, String author, String favorite,
                                              Integer limit, Integer offset) {
        User loggedInUser = userService.getLoggedInUser();
        Map<String, Object> resultWrapper = articleRepository.getListArticle(tag, author, favorite, limit, offset);
        List<Article> listArticles = (List<Article>) resultWrapper.get("listArticles");
        long countArticle = (long) resultWrapper.get("totalArticle");

        List<ArticleDTOResponse> listArticleDTOResponses = listArticles.stream()
                .map(article -> {
                    boolean isFollowing = false;
                    boolean isFavorite = false;
                    for (User u : article.getAuthor().getFollowers()) {
                        if (u.getId() == (loggedInUser.getId())) {
                            isFollowing = true;
                            break;
                        }
                    }

                    for (User u : article.getUsersFavorite()) {
                        if (u.getId() == (loggedInUser.getId())) {
                            isFavorite = true;
                            break;
                        }
                    }
                    return ArticleMapper.toArticleDTOResponse(article, isFavorite, isFollowing);
                })
                .collect(Collectors.toList());
        Map<String, Object> wrapper = new HashMap<>();
        wrapper.put("articles", listArticleDTOResponses);
        wrapper.put("articlesCount", countArticle);
        return wrapper;

    }


}
