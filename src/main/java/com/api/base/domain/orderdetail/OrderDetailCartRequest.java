package com.api.base.domain.orderdetail;

import com.api.base.domain.cart.CartCreateRequest;
import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailCartRequest {
    private CartCreateRequest cartCreateRequest;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String nameCustomer;
    @NotBlank
    private String address;
}
