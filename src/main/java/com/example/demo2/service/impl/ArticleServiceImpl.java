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
import com.example.demo2.repository.UserRepository;
import com.example.demo2.service.ArticleService;
import com.example.demo2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.*;

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
        article.setAuthor(userService.getLoggedInUser());
        article = articleRepository.save(article);
        Map<String, ArticleDTOResponse> wrapper = new HashMap<>();
        wrapper.put("article", ArticleMapper.toArticleDTOResponse(article, false, 0, false));
        return wrapper;
    }

    @Override
    public Map<String, ArticleDTOResponse> getArticleBySlug(String slug) throws CustomNotFoundException {
        Optional<Article> optionalArticle = articleRepository.findArticleBySlug(slug);
        if(!optionalArticle.isPresent()){
            throw new CustomNotFoundException(CustomError.builder().code("404").message("Not found article").build());
        }
            User userLoggedIn = userService.getLoggedInUser();
            User user = optionalArticle.get().getAuthor();
            boolean isFollowing = false;
            for (User u : user.getFollowers()) {
                if (u.getId() == (userLoggedIn.getId())) {
                    isFollowing = true;
                    break;
                }
            }
            ArticleDTOResponse articleDTOResponse = ArticleMapper.toArticleDTOResponse(optionalArticle.get(),
                    false, 0, isFollowing);

        Map<String, ArticleDTOResponse> wrapper = new HashMap<>();
        wrapper.put("article", articleDTOResponse);

        return wrapper;
    }
}
