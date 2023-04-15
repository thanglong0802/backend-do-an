package com.api.base.domain.category;

import com.api.base.utils.Annotations;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryRequest {

    @ApiModelProperty(value = "Category")
    @Annotations.Operator(value = "LIKE")
    private String name;
}
