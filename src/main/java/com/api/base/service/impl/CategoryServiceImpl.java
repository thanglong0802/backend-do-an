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

import java.util.ArrayList;
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
        simpleQueryBuilder.from("tbl_category");
        simpleQueryBuilder.where(whereClause.toString());

        PagingResponse pagingResponse = commonService.executeSearchData(pageable, simpleQueryBuilder, params, Category.class);
        List<Category> categoryList = (List<Category>) pagingResponse.getData();
        List<CategoryResponse> categoryResponses = Utilities.copyProperties(categoryList, CategoryResponse.class);
        pagingResponse.setData(categoryResponses);
        return pagingResponse;
    }

    @Override
    public List<CategoryResponse> getParentCategory() {
        List<Category> list = categoryRepository.findCategoryByParentIdIsNull();
        return Utilities.copyProperties(list, CategoryResponse.class);
    }

    @Override
    public List<CategoryResponse> getAllProductsInTheCategory(Long id) {
        List<Category> list = categoryRepository.getAllProductsInTheCategory(id);
        return Utilities.copyProperties(list, CategoryResponse.class);
    }

    @Override
    public List<CategoryResponse> directoryList(Long id) {
        List<Category> categoryList = categoryRepository.directoryList(id);
        return Utilities.copyProperties(categoryList, CategoryResponse.class);
    }

//    private String path(Category category) {
//        if(category.getParentId() == null){
//            return ">" + category.getId();
//        } else{
//            Category category1 = categoryRepository.getOne(category.getParentId());
//            String path = category1.getPath() + ">" + category.getId();
//            return path;
//        }
//
//    }

    @Override
    public CategoryResponse insert(CategoryCreateRequest request) {
        Category category = Utilities.copyProperties(request, Category.class);
        categoryRepository.save(category);
        return Utilities.copyProperties(category, CategoryResponse.class);
    }

    private Category getCategoryById(Long id) {
        Category category = Utilities.returnNullInException(() -> categoryRepository.findById(id).get());
        if (ObjectUtils.allNull(category)) {
            throw new BusinessException(MessageCode.ERR_404.name(), messageUtil.getMessage(MessageCode.BASE_01001.name()), "Category id: " + id);
        }
        return category;
    }

    @Override
    public CategoryDetailResponse detail(Long id) {
        Category category = getCategoryById(id);
        return Utilities.copyProperties(category, CategoryDetailResponse.class);
    }

    @Override
    public CategoryResponse update(CategoryUpdateRequest request) {
        Category category = getCategoryById(request.getId());
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
                throw new BusinessException(MessageCode.ERR_404.name(), messageUtil.getMessage(MessageCode.BASE_01001.name()), "Category id: " + ids);
            }
            categoryRepository.deleteAll(category);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
