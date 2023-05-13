package com.api.base.service;

import com.api.base.domain.productimage.ProductImageCreateRequest;
import com.api.base.domain.productimage.ProductImageResponse;
import com.api.base.domain.productimage.ProductImageUpdateRequest;

import java.util.List;

public interface ProductImageService {
    List<ProductImageResponse> findByProductId(Long id);
    ProductImageResponse insert(ProductImageCreateRequest request);
    ProductImageResponse update(ProductImageUpdateRequest request);
    Boolean delete(Long id);
    Boolean deleteAll(List<Long> ids);
}
