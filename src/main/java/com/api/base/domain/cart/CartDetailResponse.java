package com.api.base.domain.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartDetailResponse {

    private Long id;
    private String nameProduct;
    private Integer quantityProduct;
    private Double price;
    private Double totalPrice;

}
