package com.api.base.entity;

import com.api.base.utils.enumerate.ProductStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tbl_product")
@Getter
@Setter
public class Product extends BaseEntity {

    @Column(name = "category_id")
    private Long categoriesId;
    private String name;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private Double price;
    private Integer quantity;
    private String description;
    private String use;
    private String producer;

    @Column(name = "where_production")
    private String whereProduction;
}
