package com.api.base.domain.sample;

import java.time.Instant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class SampleCreateRequest {

    @NotBlank
    @ApiModelProperty(value = "Tên")
    private String name;

    @NotNull
    @ApiModelProperty(value = "Ngày sinh")
    private Instant birthDay;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Instant birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public String toString() {
        return "SampleCreateRequest [name=" + name + ", birthDay=" + birthDay + "]";
    }

}
