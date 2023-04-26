package com.api.base.domain.productimage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public class ProductImageResponse {
    private Long id;
    private Instant createdAt;
    private String createdBy;
    private String updatedBy;
    private Instant updatedAt;
    private Long productId;
    private String imageUrl;
}
