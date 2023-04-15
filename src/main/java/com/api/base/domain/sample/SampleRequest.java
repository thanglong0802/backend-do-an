package com.api.base.domain.sample;

import com.api.base.utils.Annotations;
import io.swagger.annotations.ApiModelProperty;

public class SampleRequest {

    @ApiModelProperty(value = "Tên sample")
    @Annotations.Operator(value = "LIKE")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SampleRequest [name=" + name + "]";
    }

}
