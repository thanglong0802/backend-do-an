package com.api.base.domain.productimage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ProductImageUpdateRequest extends ProductImageCreateRequest {

    @NotNull
    @ApiModelProperty(value = "ID ảnh")
    private Long id;
}
