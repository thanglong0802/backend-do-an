package com.api.base.domain.orderdetail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

@Getter
@Setter
@ToString
public class OrderDetailResponse {

    private Long id;

    private Long cartId;

    private String orderDetailId;

    private String nameCustomer;

    private String address;

    private String phoneNumber;

//    @Enumerated(EnumType.STRING)
    private String status;

}
