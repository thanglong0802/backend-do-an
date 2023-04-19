package com.api.base.entity;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_sample")
public class Sample extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    @Column(name = "birth_day")
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

}
