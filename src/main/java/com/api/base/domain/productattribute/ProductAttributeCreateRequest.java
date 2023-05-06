package com.api.base.domain.productattribute;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class ProductAttributeCreateRequest {
    @NotBlank
    @ApiModelProperty(value = "Tên thuộc tính")
    private String nameAttribute;
}
