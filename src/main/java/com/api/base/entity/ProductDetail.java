package com.api.base.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_product_detail_attribute")
@Getter
@Setter
public class ProductDetail extends BaseEntity {

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "attribute_id")
    private Long attributeId;

    @Column(name = "attribute_value")
    private Long attributeValue;
}
