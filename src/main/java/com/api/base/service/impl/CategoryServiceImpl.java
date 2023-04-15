package com.api.base.service.impl;

import com.api.base.domain.PagingResponse;
import com.api.base.domain.category.*;
import com.api.base.entity.Category;
import com.api.base.exception.BusinessException;
import com.api.base.repository.CategoryRepository;
import com.api.base.service.CategoryService;
import com.api.base.service.CommonService;
import com.api.base.utils.MessageUtil;
import com.api.base.utils.SimpleQueryBuilder;
import com.api.base.utils.Utilities;
import com.api.base.utils.enumerate.MessageCode;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final MessageUtil messageUtil;

    private final CommonService commonService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, MessageUtil messageUtil, CommonService commonService) {
        this.categoryRepository = categoryRepository;
        this.messageUtil = messageUtil;
        this.commonService = commonService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PagingResponse search(CategoryRequest request, Pageable pageable) {
        // luôn where với điều kiện 1 = 1 là true <=> select * from U where true
        StringBuilder whereClause = new StringBuilder("1 = 1");
        SimpleQueryBuilder simpleQueryBuilder = new SimpleQueryBuilder();
        Map<String, Object> params = new HashMap<>();
        whereClause.append(Utilities.buildWhereClause(request, params));

        // from category where 1 = 1
        simpleQueryBuilder.from("category");
        simpleQueryBuilder.where(whereClause.toString());

        PagingResponse pagingResponse = commonService.executeSearchData(pageable, simpleQueryBuilder, params, Category.class);
        List<Category> categoryList = (List<Category>) pagingResponse.getData();
        List<CategoryResponse> categoryResponses = Utilities.copyProperties(categoryList, CategoryResponse.class);
        pagingResponse.setData(categoryResponses);
        return pagingResponse;
    }

    @Override
    public List<CategoryResponse> getAll() {
        List<Category> categoryList = categoryRepository.findAll();
        return Utilities.copyProperties(categoryList, CategoryResponse.class);
    }

    @Override
    public CategoryResponse insert(CategoryCreateRequest request) {
        Category category = Utilities.copyProperties(request, Category.class);
        categoryRepository.save(category);
        return Utilities.copyProperties(category, CategoryResponse.class);
    }

    private Category getCategoriesById(Long id) {
        Category category = Utilities.returnNullInException(() -> categoryRepository.findById(id).get());
        if (ObjectUtils.allNull(category)) {
            throw new BusinessException(MessageCode.ERR_404.name(), messageUtil.getMessage(MessageCode.BASE_01001.name()), "Categories id: " + id);
        }
        return category;
    }

    @Override
    public CategoryDetailResponse detail(Long id) {
        Category category = getCategoriesById(id);
        return Utilities.copyProperties(category, CategoryDetailResponse.class);
    }

    @Override
    public CategoryResponse update(CategoryUpdateRequest request) {
        Category category = getCategoriesById(request.getId());
        Utilities.updateProperties(request, category);
        categoryRepository.save(category);
        return Utilities.copyProperties(category, CategoryResponse.class);
    }

    @Override
    public Boolean delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleteAll(List<Long> ids) {
        try {
            List<Category> category = categoryRepository.findAllById(ids);
            if (ids.size() != category.size()) {
                throw new BusinessException(MessageCode.ERR_404.name(), messageUtil.getMessage(MessageCode.ERR_404.name()), "Categories id: " + ids);
            }
            categoryRepository.deleteAll(category);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
