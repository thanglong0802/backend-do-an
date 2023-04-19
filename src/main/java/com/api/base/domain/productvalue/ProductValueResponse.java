package com.api.base.domain.productvalue;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductValueResponse {
    private Long id;
    private Long productAttributeId;
    private String name;
}
