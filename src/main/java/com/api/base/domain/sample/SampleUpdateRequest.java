package com.api.base.domain.sample;

import javax.validation.constraints.NotNull;

public class SampleUpdateRequest extends SampleCreateRequest {

    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SampleUpdateRequest [id=" + id + ", name=" + getName() + ", birthDay=" + getBirthDay() + "]";
    }

}
