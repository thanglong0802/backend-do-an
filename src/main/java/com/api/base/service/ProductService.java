package com.api.base.service;

import com.api.base.domain.PagingResponse;
import com.api.base.domain.product.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    PagingResponse search(ProductRequest request, Pageable pageable);
    ProductResponse insert(ProductCreateRequest request);
    ProductResponse update(ProductUpdateRequest request);
    ProductDetailResponse detail(Long id);
    Boolean delete(Long id);
    Boolean deleteAll(List<Long> ids);
}
