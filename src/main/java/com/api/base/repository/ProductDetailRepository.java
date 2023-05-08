package com.api.base.repository;

import com.api.base.domain.productdetail.ProductDetailResponseWithAttribute;
import com.api.base.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

    @Query(value = "SELECT new com.api.base.domain.productdetail.ProductDetailResponseWithAttribute(d.id as id, c.name_category as nameCategory, a.name_attribute as nameAttribute, v.name_value as nameValue) \n" +
                        "FROM tbl_product_detail_attribute d \n" +
                        "INNER JOIN tbl_category c ON d.category_id = c.id \n" +
                        "INNER JOIN tbl_product_attribute a ON d.attribute_id = a.id \n" +
                        "INNER JOIN tbl_product_value v ON d.attribute_value = v.id", nativeQuery = true)
    List<ProductDetailResponseWithAttribute> searchProductByCategoryAndAttribute();
}
