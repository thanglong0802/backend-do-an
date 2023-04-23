package com.api.base.service;

import com.api.base.domain.PagingResponse;
import com.api.base.domain.productvalue.ProductValueCreateRequest;
import com.api.base.domain.productvalue.ProductValueRequest;
import com.api.base.domain.productvalue.ProductValueResponse;
import com.api.base.domain.productvalue.ProductValueUpdateRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductValueService {
    ProductValueResponse insert(ProductValueCreateRequest request);
    ProductValueResponse update(ProductValueUpdateRequest request);
    PagingResponse search(ProductValueRequest request, Pageable pageable);
    Boolean delete(Long id);
    Boolean deleteAll(List<Long> ids);
}
