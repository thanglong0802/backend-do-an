package com.api.base.domain.category;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class CategoryCreateRequest {

    @NotBlank
    @ApiModelProperty(value = "Tên danh mục")
    private String name;

    @ApiModelProperty(value = "ID danh mục cha")
    private Long parentId;

}
