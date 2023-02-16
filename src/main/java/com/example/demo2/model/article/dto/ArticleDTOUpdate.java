package com.example.demo2.model.article.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDTOUpdate {
    private String title;
    private String description;
    private String body;
}

