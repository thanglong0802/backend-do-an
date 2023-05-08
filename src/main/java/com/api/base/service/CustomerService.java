package com.api.base.service;

import com.api.base.domain.PagingResponse;
import com.api.base.domain.customer.CustomerCreateRequest;
import com.api.base.domain.customer.CustomerRequest;
import com.api.base.domain.customer.CustomerResponse;
import com.api.base.domain.customer.CustomerUpdateRequest;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    PagingResponse search(CustomerRequest request, Pageable pageRequest);
    CustomerResponse insert(CustomerCreateRequest request);
    CustomerResponse update(CustomerUpdateRequest request);
    Boolean delete(Long id);

}
