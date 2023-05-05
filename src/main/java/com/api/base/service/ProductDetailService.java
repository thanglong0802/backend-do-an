package com.api.base.service;

import com.api.base.domain.productdetail.ProductDetailCreateRequest;
import com.api.base.domain.productdetail.ProductDetailDetailResponse;
import com.api.base.domain.productdetail.ProductDetailResponse;
import com.api.base.domain.productdetail.ProductDetailUpdateRequest;

import java.util.List;

public interface ProductDetailService {
    List<ProductDetailDetailResponse> detail();
    ProductDetailResponse insert(ProductDetailCreateRequest request);
    ProductDetailResponse update(ProductDetailUpdateRequest request);
    Boolean delete(Long id);
}
