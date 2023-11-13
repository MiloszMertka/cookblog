package com.example.cookblog.dto.resources;

import com.example.cookblog.model.Unit;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record QuantityResource(
        @Null Long id,
        @NotNull @Positive Integer amount,
        @NotNull Unit unit) {

}