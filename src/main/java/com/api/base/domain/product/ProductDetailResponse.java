package com.api.base.domain.product;

import com.api.base.utils.enumerate.ProductStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public class ProductDetailResponse {
    private Long id;
    private Instant createdAt;
    private String createdBy;
    private String updatedBy;
    private Instant updatedAt;
    private Long categoriesId;
    private String nameProduct;
    private String status;
    private Double price;
    private Integer promotionalPrice;
    private Integer quantity;
    private String description;
    private String use;
    private String producer;
    private String whereProduction;
}
