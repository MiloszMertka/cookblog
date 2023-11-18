package com.example.cookblog.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record CommentRecipeRequest(
        @NotBlank @Size(max = 255) String author,
        @NotBlank @Size(max = 2000) String content
) {

}
