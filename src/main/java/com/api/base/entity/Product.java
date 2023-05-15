package com.api.base.entity;

import com.api.base.utils.enumerate.ProductStatus;
import io.micrometer.core.annotation.Counted;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseEntity {

    @Column(name = "category_id")
    private Long categoriesId;

    @Column(name = "name_product")
    private String nameProduct;

    @Column(name = "promotional_price")
    private Integer promotionalPrice;

    @Column(name = "status")
//    @Enumerated(EnumType.STRING)
    private String status;

    private Double price;
    private Integer quantity;
    private String description;
    private String use;
    private String producer;

    @Column(name = "where_production")
    private String whereProduction;
}
