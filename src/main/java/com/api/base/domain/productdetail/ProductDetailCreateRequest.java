package com.api.base.domain.productdetail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ProductDetailCreateRequest {

    @ApiModelProperty(name = "ID danh mục")
    @NotNull
    private Long categoryId;

    @ApiModelProperty(name = "ID thuộc tính")
    @NotNull
    private Long attributeId;

    @ApiModelProperty(name = "Giá trị thuộc tính")
    @NotNull
    private Long attributeValue;

    @ApiModelProperty(name = "ID sản phẩm")
    @NotNull
    private Long productID;
}
