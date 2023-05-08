package com.api.base.domain.customer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class CustomerCreateRequest {

    @NotBlank
    private String nameCustomer;
    @NotBlank
    private String address;
    @NotBlank
    private String phoneNumber;

}
