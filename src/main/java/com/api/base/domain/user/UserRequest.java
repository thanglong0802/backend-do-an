package com.api.base.domain.user;

import com.api.base.utils.Annotations;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRequest {
    @ApiModelProperty(value = "User Name")
    @Annotations.Operator(value = "LIKE")
    private String userName;
}
