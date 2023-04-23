package com.api.base.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_category")
@Getter
@Setter
public class Category extends BaseEntity {
    private String name;

    @Column(name = "parent_id")
    private Long parentId;
}
