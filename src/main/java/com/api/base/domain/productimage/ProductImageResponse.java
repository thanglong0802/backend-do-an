package com.api.base.domain.productimage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductImageResponse {
    private Long id;
    private Long productId;
    private String imageUrl;
}
