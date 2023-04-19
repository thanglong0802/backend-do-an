package com.api.base.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_product_attribute")
@Getter
@Setter
public class ProductAttribute extends BaseEntity {
    @Column(name = "name")
    private String name;
}
