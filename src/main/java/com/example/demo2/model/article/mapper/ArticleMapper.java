package com.example.demo2.model.article.mapper;

import com.example.demo2.entity.Article;
import com.example.demo2.entity.Tag;
import com.example.demo2.entity.User;
import com.example.demo2.model.article.dto.ArticleDTOCreate;
import com.example.demo2.model.article.dto.ArticleDTOResponse;
import com.example.demo2.model.article.dto.AuthorDTOResponse;
import com.example.demo2.util.SlugUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class ArticleMapper {
    public static ArticleDTOResponse toArticleDTOResponse(Article article, boolean isFavorite,
                                                          boolean isFollowing) {

        return ArticleDTOResponse.builder().slug(article.getSlug()).title(article.getTitle())
                .description(article.getDescription()).body(article.getBody()).tags(article.getTagList())
                .createdAt(article.getCreatedAt()).updatedAt(article.getUpdatedAt()).isFavorite(isFavorite)
                .favoritesCount(article.getUsersFavorite().size()).tags(article.getTagList()).author(toAuthorDTOResponse(article.getAuthor(), isFollowing)).build();
    }

    public static AuthorDTOResponse toAuthorDTOResponse(User author, boolean isFollowing) {
        return AuthorDTOResponse.builder().username(author.getUsername()).bio(author.getBio()).image(author.getImage())
                .following(isFollowing).build();
    }

    public static Article toArticle(ArticleDTOCreate articleDTOCreate) {
        Date now = new Date();
        Article article = Article.builder().slug(SlugUtil.getSlug(articleDTOCreate.getTitle())).title(articleDTOCreate.getTitle())
                .description(articleDTOCreate.getDescription()).body(articleDTOCreate.getBody())
                .createdAt(now).updatedAt(now).isFavorite(false).build();
        return article;
    }
}
