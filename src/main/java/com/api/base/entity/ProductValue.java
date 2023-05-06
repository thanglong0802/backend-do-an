package com.api.base.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_product_value")
@Getter
@Setter
public class ProductValue extends BaseEntity {
    @Column(name = "product_attribute_id")
    private Long productAttributeId;

    @Column(name = "name_value")
    private String nameValue;
}
