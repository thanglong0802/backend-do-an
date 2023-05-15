package com.api.base.domain.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ProductUpdateRequest extends ProductUpdateFullRequest {
    @NotNull
    private Long id;
}
