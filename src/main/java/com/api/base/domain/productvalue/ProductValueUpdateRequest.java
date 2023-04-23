package com.api.base.domain.productvalue;

import javax.validation.constraints.NotNull;

public class ProductValueUpdateRequest extends ProductValueCreateRequest {
    @NotNull
    private Long id;
}
