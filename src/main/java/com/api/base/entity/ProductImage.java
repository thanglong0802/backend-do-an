package com.api.base.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_product_detail_image")
@Getter
@Setter
public class ProductImage extends BaseEntity {
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "image_url")
    private String imageUrl;
}
