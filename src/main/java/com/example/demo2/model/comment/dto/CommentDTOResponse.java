package com.example.demo2.model.comment.dto;

import com.example.demo2.model.article.dto.AuthorDTOResponse;
import com.example.demo2.model.user.dto.UserDTOResponse;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTOResponse {
    private Integer id;
    private Date createDate;
    private String body;
    private AuthorDTOResponse author;
}
