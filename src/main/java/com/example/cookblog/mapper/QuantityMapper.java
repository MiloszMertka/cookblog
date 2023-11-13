package com.example.cookblog.mapper;

import com.example.cookblog.dto.resources.QuantityResource;
import com.example.cookblog.model.Quantity;

public interface QuantityMapper {

    QuantityResource mapQuantityToQuantityResource(Quantity quantity);

    Quantity mapQuantityResourceToQuantity(QuantityResource quantityResource);

}
