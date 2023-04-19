package com.api.base.domain.productvalue;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ProductValueCreateRequest {
    @NotNull
    @ApiModelProperty(value = "ID thuộc tính")
    private Long productAttributeId;

    @NotBlank
    private String name;
}
