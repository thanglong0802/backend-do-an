package com.api.base.domain.productattribute;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public class ProductAttributeResponse {
    private Long id;
    private Instant createdAt;
    private String createdBy;
    private String updatedBy;
    private Instant updatedAt;
    private String nameAttribute;
    private Integer isShow;
    private Integer sort;
}
