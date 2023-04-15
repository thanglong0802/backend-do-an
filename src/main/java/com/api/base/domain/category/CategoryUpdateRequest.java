package com.api.base.domain.category;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class CategoryUpdateRequest extends CategoryCreateRequest {

    @NotNull
    private Long id;
}
