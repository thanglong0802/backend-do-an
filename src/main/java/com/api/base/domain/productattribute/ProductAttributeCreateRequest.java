package com.api.base.domain.productattribute;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ProductAttributeCreateRequest {
    @NotBlank
    @ApiModelProperty(value = "Tên thuộc tính")
    private String nameAttribute;

    @NotNull
    @ApiModelProperty(value = "Hiển thị thuộc tính")
    private Integer isShow;
}
