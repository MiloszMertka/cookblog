package com.example.cookblog.mapper.internal;

import com.example.cookblog.dto.resources.QuantityResource;
import com.example.cookblog.mapper.QuantityMapper;
import com.example.cookblog.model.Quantity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class QuantityMapperService implements QuantityMapper {

    @Override
    public QuantityResource mapQuantityToQuantityResource(Quantity quantity) {
        return QuantityResource.builder()
                .id(quantity.getId())
                .amount(quantity.getAmount())
                .unit(quantity.getUnit())
                .build();
    }

    @Override
    public Quantity mapQuantityResourceToQuantity(QuantityResource quantityResource) {
        return Quantity.builder()
                .amount(quantityResource.amount())
                .unit(quantityResource.unit())
                .build();
    }

}
