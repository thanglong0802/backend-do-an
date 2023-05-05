package com.api.base.domain.productvalue;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ProductValueUpdateRequest extends ProductValueCreateRequest {
    @NotNull
    private Long id;
}
