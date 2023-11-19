package com.example.cookblog.mapper;

import com.example.cookblog.dto.requests.CommentRecipeRequest;
import com.example.cookblog.dto.resources.CommentResource;
import com.example.cookblog.model.Comment;

public interface CommentMapper {

    CommentResource mapCommentToCommentResource(Comment comment);

    Comment mapCommentRecipeRequestToComment(CommentRecipeRequest commentRecipeRequest);

}
