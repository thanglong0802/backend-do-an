package com.api.base.service;

import com.api.base.domain.product.ProductCreateRequest;
import com.api.base.domain.product.ProductDetailResponse;
import com.api.base.domain.product.ProductResponse;
import com.api.base.domain.product.ProductUpdateRequest;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAll();
    ProductResponse insert(ProductCreateRequest request);
    ProductResponse update(ProductUpdateRequest request);
    ProductDetailResponse detail(Long id);
    Boolean delete(Long id);
    Boolean deleteAll(List<Long> ids);
}
