package com.api.base.domain.category;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryDetailResponse {
    private Long id;
    private String name;
    private Long parentId;
}