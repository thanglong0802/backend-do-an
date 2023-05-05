package com.api.base.domain.productdetail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ProductDetailUpdateRequest extends ProductDetailCreateRequest {

    @NotNull
    @ApiModelProperty(name = "ID của product detail")
    private Long id;

}
