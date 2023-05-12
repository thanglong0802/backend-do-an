package com.api.base.domain.cart;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class CartCreateRequest {

    @NotNull
    private Long productId;

    private Integer quantityProduct;

}
