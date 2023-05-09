package com.api.base.domain.orderdetail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class OrderDetailCreateRequest {

    @NotNull
    private Long cartId;
    @NotNull
    private Long orderId;

}
