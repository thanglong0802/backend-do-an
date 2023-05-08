package com.api.base.domain.productdetail;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductDetailResponseWithAttribute {
    private Long id;
    private String nameCategory;
    private String nameAttribute;
    private String nameValue;

    public ProductDetailResponseWithAttribute(Long id, String nameCategory, String nameAttribute, String nameValue) {
        this.id = id;
        this.nameCategory = nameCategory;
        this.nameAttribute = nameAttribute;
        this.nameValue = nameValue;
    }
}
