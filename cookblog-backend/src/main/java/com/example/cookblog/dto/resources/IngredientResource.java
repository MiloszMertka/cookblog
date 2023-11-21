package com.example.cookblog.dto.resources;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record IngredientResource(
        @Null Long id,
        @NotBlank @Size(max = 255) String name,
        @NotNull @Valid QuantityResource quantity
) {

}