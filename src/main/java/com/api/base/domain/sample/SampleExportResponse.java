package com.api.base.domain.sample;

import com.api.base.utils.Annotations;
import com.api.base.utils.converter.DateTimeJsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.Instant;

public class SampleExportResponse {

    @Annotations.ColumnHeader(value = "ID", order = 0)
    private Long id;

    @Annotations.ColumnHeader(value = "Tên", order = 1)
    private String name;

    @Annotations.ColumnHeader(value = "Ngày sinh", style = "date", order = 2)
    @JsonDeserialize(using = DateTimeJsonDeserializer.class)
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
