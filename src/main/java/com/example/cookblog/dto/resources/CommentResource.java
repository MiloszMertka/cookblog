package com.example.cookblog.dto.resources;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record CommentResource(
        Long id,
        String author,
        String content
) {

}