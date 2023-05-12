package com.api.base.domain.category;

import com.api.base.utils.enumerate.ProductStatus;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseProduct {

    private Long id;
    private Long categoriesId;
    private ProductStatus status;
    private Double price;
    private Integer quantity;
    private String description;
    private String use;
    private String producer;
    private String whereProduction;
    private String nameProduct;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
    private String nameCategory;
    private Long parentId;

}
