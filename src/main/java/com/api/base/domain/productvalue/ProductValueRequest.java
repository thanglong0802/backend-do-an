package com.api.base.domain.productvalue;

import com.api.base.utils.Annotations;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductValueRequest {

    @ApiModelProperty(value = "Product Value")
    @Annotations.Operator(value = "LIKE")
    private String nameValue;
}
