package com.api.base.domain.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartResponse {

    private Long id;
    private Long productId;
    private Integer quantity;

}
