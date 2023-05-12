package com.api.base.domain.productdetail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public class ProductDetailResponse {

    private Long id;
    private Long categoryId;
    private Long attributeId;
    private Long attributeValue;
    private Long productID;

}
