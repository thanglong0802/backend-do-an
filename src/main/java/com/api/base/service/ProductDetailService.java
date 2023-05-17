package com.api.base.service;

import com.api.base.domain.productdetail.*;

import java.util.List;

public interface ProductDetailService {
    List<ProductDetailDetailResponse> detail();
    ProductDetailResponse insert(ProductDetailCreateRequest request);
    ProductDetailResponse update(ProductDetailUpdateRequest request);
    Boolean delete(Long id);
    List<ProductDetailResponseWithAttribute> searchProductByCategoryAndAttribute();
    List<ProductDetailAttributeOfProductResponse> attributeOfProduct(Long id);
}
