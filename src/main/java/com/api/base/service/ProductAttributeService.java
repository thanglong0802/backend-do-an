package com.api.base.service;

import com.api.base.domain.PagingResponse;
import com.api.base.domain.productattribute.ProductAttributeCreateRequest;
import com.api.base.domain.productattribute.ProductAttributeRequest;
import com.api.base.domain.productattribute.ProductAttributeResponse;
import com.api.base.domain.productattribute.ProductAttributeUpdateRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductAttributeService {
    PagingResponse search(ProductAttributeRequest request, Pageable pageable);
    ProductAttributeResponse insert(ProductAttributeCreateRequest request);
    ProductAttributeResponse detail(Long id);
    ProductAttributeResponse update(ProductAttributeUpdateRequest request);
    Boolean delete(Long id);
    Boolean deleteAll(List<Long> ids);
}
