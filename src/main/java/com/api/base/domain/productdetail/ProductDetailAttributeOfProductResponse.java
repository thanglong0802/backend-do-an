package com.api.base.domain.productdetail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDetailAttributeOfProductResponse {
    private Long id;
    private String nameAttribute;
    private String nameValue;
    private Long productID;
    private Integer isShow;
    private Integer sort;
}
