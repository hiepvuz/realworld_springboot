package com.example.demo2.model.comment.dto.mapper;

import com.example.demo2.entity.Comment;
import com.example.demo2.model.article.mapper.ArticleMapper;
import com.example.demo2.model.comment.dto.CommentDTOCreate;
import com.example.demo2.model.comment.dto.CommentDTOResponse;
import com.example.demo2.model.user.mapper.UserMapper;

import java.util.Date;

public class CommentMapper {
    public static Comment toComment(CommentDTOCreate commentDTOCreate){
        return Comment.builder().createDate(new Date())
                .body(commentDTOCreate.getBody()).build();
    }

    public static CommentDTOResponse toCommentDTOResponse(Comment comment, boolean isFollowing){
        return CommentDTOResponse.builder().id(comment.getId()).createDate(comment.getCreateDate())
                .body(comment.getBody())
                .author(ArticleMapper.toAuthorDTOResponse(comment.getAuthor(), isFollowing)).build();
    }

}
