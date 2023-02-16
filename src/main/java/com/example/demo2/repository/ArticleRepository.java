package com.example.demo2.repository;


import com.example.demo2.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer>, ArticleRepositoryCustom {
    Optional<Article> findArticleBySlug(String slug);

    @Query(value = "SELECT * FROM Article a JOIN users u ON a.author_id = u.id JOIN user_follow uf ON a.author_id = uf.user_id WHERE uf.follower_id = 1"
            ,nativeQuery = true)
    List<Article> getFeed();
}
