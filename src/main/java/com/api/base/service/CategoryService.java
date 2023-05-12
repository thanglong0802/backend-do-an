package com.api.base.service;

import com.api.base.domain.PagingResponse;
import com.api.base.domain.category.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    PagingResponse search(CategoryRequest request, Pageable pageable);
    List<CategoryResponse> getParentCategory();

    List<CategoryResponseProduct> getAllProductsInTheCategory(Long id);

    List<CategoryResponse> directoryList(Long id);

    CategoryResponse insert(CategoryCreateRequest request);

    CategoryDetailResponse detail(Long id);

    CategoryResponse update(CategoryUpdateRequest request);

    Boolean delete(Long id);

    Boolean deleteAll(List<Long> ids);

}
