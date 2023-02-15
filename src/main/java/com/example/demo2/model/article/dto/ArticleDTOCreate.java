package com.example.demo2.model.article.dto;

import com.example.demo2.entity.Tag;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDTOCreate {
    private String title;
    private String description;
    private String body;
    private List<Integer> tagList;
}
