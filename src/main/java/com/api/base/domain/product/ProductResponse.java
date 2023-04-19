package com.api.base.domain.product;

import com.api.base.utils.enumerate.ProductStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductResponse {
    private Long id;
    private Long categoriesId;
    private String name;
    private ProductStatus status;
    private Double price;
    private Integer quantity;
    private String description;
    private String use;
    private String producer;
    private String whereProduction;
}
