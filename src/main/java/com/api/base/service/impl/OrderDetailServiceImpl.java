package com.api.base.service.impl;

import com.api.base.domain.orderdetail.OrderDetailCreateRequest;
import com.api.base.domain.orderdetail.OrderDetailResponse;
import com.api.base.entity.OrderDetail;
import com.api.base.repository.OrderDetailRepository;
import com.api.base.service.OrderDetailService;
import com.api.base.utils.Utilities;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public List<OrderDetailResponse> getAll() {
        List<OrderDetail> orderDetail = orderDetailRepository.findAll();
        return Utilities.copyProperties(orderDetail, OrderDetailResponse.class);
    }

    @Override
    public OrderDetailResponse insert(OrderDetailCreateRequest request) {
        OrderDetail orderDetail = Utilities.copyProperties(request, OrderDetail.class);
        orderDetailRepository.save(orderDetail);
        return Utilities.copyProperties(orderDetail, OrderDetailResponse.class);
    }
}
