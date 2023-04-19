package com.api.base.domain.productimage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ProductImageCreateRequest {
    @NotNull
    @ApiModelProperty(value = "ID sản phẩm")
    private Long productId;

    @NotBlank
    @ApiModelProperty(value = "ảnh sản phẩm")
    private String imageUrl;
}
