package com.api.base.service;

import com.api.base.domain.customer.CustomerCreateRequest;
import com.api.base.domain.orderdetail.OrderDetailCartRequest;
import com.api.base.domain.orderdetail.OrderDetailCreateRequest;
import com.api.base.domain.orderdetail.OrderDetailResponse;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetailResponse> getAll();
    OrderDetailResponse insert(OrderDetailCreateRequest request);
    OrderDetailResponse insertNewCart(OrderDetailCartRequest request);

}
