package com.example.cookblog.dto.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record ImageResource(
        @Null Long id,
        @NotBlank @Size(max = 255) String path
) {

}