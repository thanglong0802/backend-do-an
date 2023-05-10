package com.api.base.domain.orderdetail;

import com.api.base.utils.enumerate.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@ToString
public class OrderDetailResponse {

    private Long id;

    private Long cartId;

    private Long customerId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Double totalPrice;

}
