package com.api.base.domain.productdetail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public class ProductDetailDetailResponse {

    private Long id;
    private Instant createdAt;
    private String createdBy;
    private String updatedBy;
    private Instant updatedAt;
    private Long categoryId;
    private Long attributeId;
    private Long attributeValue;

}
