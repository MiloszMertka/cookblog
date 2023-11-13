package com.example.cookblog.mapper.internal;

import com.example.cookblog.dto.requests.CommentRecipeRequest;
import com.example.cookblog.dto.resources.CommentResource;
import com.example.cookblog.mapper.CommentMapper;
import com.example.cookblog.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CommentMapperService implements CommentMapper {

    @Override
    public CommentResource mapCommentToCommentResource(Comment comment) {
        return CommentResource.builder()
                .id(comment.getId())
                .author(comment.getAuthor())
                .content(comment.getContent())
                .build();
    }

    @Override
    public Comment mapCommentRecipeRequestToComment(CommentRecipeRequest commentRecipeRequest) {
        return Comment.builder()
                .author(commentRecipeRequest.author())
                .content(commentRecipeRequest.content())
                .build();
    }

}
