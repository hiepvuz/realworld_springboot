package com.example.demo2.service.impl;

import com.example.demo2.entity.Article;
import com.example.demo2.entity.User;
import com.example.demo2.exception.custom.CustomNotFoundException;
import com.example.demo2.model.article.dto.ArticleDTOCreate;
import com.example.demo2.model.article.dto.ArticleDTOResponse;
import com.example.demo2.model.article.dto.AuthorDTOResponse;
import com.example.demo2.model.article.mapper.ArticleMapper;
import com.example.demo2.repository.ArticleRepository;
import com.example.demo2.repository.TagRepository;
import com.example.demo2.service.UserService;
import com.example.demo2.util.SlugUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Mock
    private SlugUtil slugUtil;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private ArticleMapper articleMapper;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private UserService userService;

    @Test
    void create() {
        //given
        User author = new User();
        author.setFollowers(new HashSet<>());
        ArticleDTOCreate articleDTOCreate = ArticleDTOCreate.builder().title("Unit test article title")
                .description("UnitTest article description").body("Unit Test article body")
                .tagList(Arrays.asList()).build();
        Map<String, ArticleDTOCreate> articleDTOCreateMap = new HashMap<>();
        articleDTOCreateMap.put("article", articleDTOCreate);
        Map<String, ArticleDTOResponse> expected = new HashMap<>();
        ArticleDTOResponse articleDTOResponseExpected = ArticleDTOResponse.builder().slug("valid-slug").title("Unit test article title")
                .description("UnitTest article description").body("Unit Test article body").tags(new HashSet<>())
                .createdAt(new Date()).updatedAt(new Date()).isFavorite(false).favoritesCount(0)
                .author(new AuthorDTOResponse("usernameAuthor", "bioAuthor", "imageAuthor", false)).build();
        expected.put("article", articleDTOResponseExpected);

        //when
        BDDMockito.given(slugUtil.getSlug(articleDTOCreate.getTitle())).willReturn("valid-slug");
        when(tagRepository.findByIdIn(articleDTOCreate.getTagList())).thenReturn(new ArrayList<>());
        Article article = Article.builder().slug("valid-slug").title("Unit test article title")
                .description("UnitTest article description").body("Unit Test article body").tagList(new HashSet<>())
                .createdAt(new Date()).updatedAt(new Date()).isFavorite(false).favoritesCount(0).author(author)
                .tagList(new HashSet<>()).usersFavorite(new HashSet<>()).build();

        BDDMockito.given(articleMapper.toArticle(articleDTOCreate)).willReturn(article);
        when(articleMapper.toArticleDTOResponse(article, false, false)).thenReturn(articleDTOResponseExpected);
        when(userService.getLoggedInUser()).thenReturn(new User());
        Map<String, ArticleDTOResponse> actual = articleService.create(articleDTOCreateMap);

        //then
        assertEquals(true, actual.containsKey("article"));
        ArticleDTOResponse articleDTOResponse = actual.get("article");
        assertEquals(expected.get("article").getSlug(), articleDTOResponse.getSlug());
        assertEquals(expected.get("article").getTitle(), articleDTOResponse.getTitle());
        assertEquals(expected.get("article").getBody(), articleDTOResponse.getBody());
        assertEquals(expected.get("article").getDescription(), articleDTOResponse.getDescription());
        assertEquals(expected.get("article").getAuthor(), articleDTOResponse.getAuthor());
        assertEquals(expected.get("article").getCreatedAt(), articleDTOResponse.getCreatedAt());
        assertEquals(expected.get("article").getUpdatedAt(), articleDTOResponse.getUpdatedAt());
        assertEquals(expected.get("article").getTags(), articleDTOResponse.getTags());
        assertEquals(expected.get("article").getFavoritesCount(), articleDTOResponse.getFavoritesCount());
        assertEquals(expected.get("article").isFavorite(), articleDTOResponse.isFavorite());
    }

    @Test
    void getArticleBySlug() throws CustomNotFoundException {
        //given
        String slug = "unittest-slug";
        User author = new User();
        author.setFollowers(new HashSet<>());
        Optional<Article> optionalArticle = Optional.of(Article.builder().slug("unittest-slug")
                .usersFavorite(new HashSet<>()).author(author).build());
        ArticleDTOResponse articleDTOResponseExpected = ArticleDTOResponse.builder().slug("unittest-slug").build();
        Map<String, ArticleDTOResponse> expected = new HashMap<>();
        expected.put("article", articleDTOResponseExpected);

        //when
        when(articleRepository.findArticleBySlug(slug)).thenReturn(optionalArticle);
        Map<String, ArticleDTOResponse> actual = articleService.getArticleBySlug(slug);

        //then
        assertTrue(actual.containsKey("article"));
        assertEquals(articleDTOResponseExpected.getSlug(), actual.get("article").getSlug());
    }
}