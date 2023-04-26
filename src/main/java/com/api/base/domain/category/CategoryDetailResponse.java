package com.api.base.domain.category;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public class CategoryDetailResponse {
    private Long id;
    private Instant createdAt;
    private String createdBy;
    private String updatedBy;
    private Instant updatedAt;
    private String name;
    private Long parentId;
}
