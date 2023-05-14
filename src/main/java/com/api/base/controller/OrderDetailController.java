package com.api.base.controller;

import com.api.base.domain.orderdetail.OrderDetailCartRequest;
import com.api.base.domain.orderdetail.OrderDetailCreateRequest;
import com.api.base.domain.orderdetail.OrderDetailResponse;
import com.api.base.service.OrderDetailService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order-detail")
@Api(description = "Module Order Detail")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailResponse>> getAll() {
        return ResponseEntity.ok(orderDetailService.getAll());
    }

    @PostMapping
    public ResponseEntity<OrderDetailResponse> insert(@Valid @RequestBody OrderDetailCreateRequest request) {
        return ResponseEntity.ok(orderDetailService.insert(request));
    }

    @PostMapping("/new-cart")
    public ResponseEntity<OrderDetailResponse> insertNewOrder(@RequestBody OrderDetailCartRequest request) {
        return ResponseEntity.ok(orderDetailService.insertNewCart(request));
    }
}
