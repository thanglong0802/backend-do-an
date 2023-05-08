package com.api.base.domain.customer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerResponse {

    private Long id;
    private String nameCustomer;
    private String address;
    private String phoneNumber;

}
