package com.api.base.domain.customer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class CustomerUpdateRequest extends CustomerCreateRequest {

    @NotNull
    private Long id;

}
