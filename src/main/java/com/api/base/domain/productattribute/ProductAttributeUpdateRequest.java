package com.api.base.domain.productattribute;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ProductAttributeUpdateRequest extends ProductAttributeCreateRequest {
    @NotNull
    private Long id;
}
