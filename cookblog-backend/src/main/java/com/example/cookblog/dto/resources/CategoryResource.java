package com.example.cookblog.dto.resources;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder
@Jacksonized
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record CategoryResource(
        @Null Long id,
        @NotBlank @Size(max = 255) String name,
        @Null @JsonManagedReference List<RecipeResource> recipes
) {

}