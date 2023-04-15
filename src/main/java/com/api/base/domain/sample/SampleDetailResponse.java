package com.api.base.domain.sample;

import com.api.base.utils.converter.DateTimeJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.Instant;

public class SampleDetailResponse {

    private Long id;
    private String name;
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Instant birthDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

}
