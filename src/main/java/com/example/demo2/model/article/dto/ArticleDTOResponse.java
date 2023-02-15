package com.example.demo2.model.article.dto;

import com.example.demo2.entity.Tag;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDTOResponse {
    private String slug;
    private String title;
    private String description;
    private String body;
    private Set<Tag> tags;
    private Date createdAt;
    private Date updatedAt;
    private boolean isFavorite;
    private int favoritesCount;
    private AuthorDTOResponse author;
}
