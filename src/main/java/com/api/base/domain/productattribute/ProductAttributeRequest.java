package com.api.base.domain.productattribute;

import com.api.base.utils.Annotations;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductAttributeRequest {
    @ApiModelProperty(value = "Product Attribute")
    @Annotations.Operator(value = "LIKE")
    private String nameAttribute;
}
