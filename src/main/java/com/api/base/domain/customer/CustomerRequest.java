package com.api.base.domain.customer;

import com.api.base.utils.Annotations;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerRequest {

    @ApiModelProperty(value = "Số điện thoại khách hàng")
    @Annotations.Operator(value = "LIKE")
    private String phoneNumber;

}
