package com.api.base.entity;

import com.api.base.utils.enumerate.ProductStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product extends BaseEntity {

    @Column(name = "categories_id")
    private Long categoriesId;
    private String name;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(name = "thumb_img")
    private String thumbImg;
    private Double price;
    private Integer quantity;
    private String description;
    private String use;
    private String producer;

    @Column(name = "where_production")
    private String whereProduction;
}
