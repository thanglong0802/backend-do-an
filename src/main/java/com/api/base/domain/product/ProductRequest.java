package com.api.base.domain.product;

import com.api.base.utils.Annotations;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductRequest {

    @ApiModelProperty(value = "Product")
    @Annotations.Operator(value = "LIKE")
    private String nameProduct;
}
