package com.api.base.service.impl;

import com.api.base.domain.PagingResponse;
import com.api.base.domain.productattribute.ProductAttributeCreateRequest;
import com.api.base.domain.productattribute.ProductAttributeRequest;
import com.api.base.domain.productattribute.ProductAttributeResponse;
import com.api.base.domain.productattribute.ProductAttributeUpdateRequest;
import com.api.base.entity.ProductAttribute;
import com.api.base.exception.BusinessException;
import com.api.base.repository.ProductAttributeRepository;
import com.api.base.service.CommonService;
import com.api.base.service.ProductAttributeService;
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
public class ProductAttributeServiceImpl implements ProductAttributeService {

    private final CommonService commonService;
    private final ProductAttributeRepository attributeRepository;
    private final MessageUtil messageUtil;

    public ProductAttributeServiceImpl(CommonService commonService, ProductAttributeRepository attributeRepository, MessageUtil messageUtil) {
        this.commonService = commonService;
        this.attributeRepository = attributeRepository;
        this.messageUtil = messageUtil;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PagingResponse search(ProductAttributeRequest request, Pageable pageable) {
        StringBuilder where = new StringBuilder("1 = 1");
        SimpleQueryBuilder simpleQueryBuilder = new SimpleQueryBuilder();
        Map<String, Object> params = new HashMap<>();
        where.append(Utilities.buildWhereClause(request, params));

        simpleQueryBuilder.from("tbl_product_attribute");
        simpleQueryBuilder.where(where.toString());

        PagingResponse pagingResponse = commonService.executeSearchData(pageable, simpleQueryBuilder, params, ProductAttribute.class);
        List<ProductAttribute> attributeList = (List<ProductAttribute>) pagingResponse.getData();
        List<ProductAttributeResponse> attributeResponses = Utilities.copyProperties(attributeList, ProductAttributeResponse.class);
        pagingResponse.setData(attributeResponses);
        return pagingResponse;
    }

    @Override
    public ProductAttributeResponse insert(ProductAttributeCreateRequest request) {
        ProductAttribute productAttribute = Utilities.copyProperties(request, ProductAttribute.class);
        attributeRepository.save(productAttribute);
        return Utilities.copyProperties(productAttribute, ProductAttributeResponse.class);
    }

    private ProductAttribute getById(Long id) {
        ProductAttribute productAttribute = Utilities.returnNullInException(() -> attributeRepository.findById(id).get());
        if (ObjectUtils.allNull(productAttribute)) {
            throw new BusinessException(MessageCode.ERR_404.name(), messageUtil.getMessage(MessageCode.BASE_04001.name()), "Product Attribute ID: " + id);
        }
        return productAttribute;
    }

    @Override
    public ProductAttributeResponse detail(Long id) {
        ProductAttribute productAttribute = getById(id);
        return Utilities.copyProperties(productAttribute, ProductAttributeResponse.class);
    }

    @Override
    public ProductAttributeResponse update(ProductAttributeUpdateRequest request) {
        ProductAttribute productAttribute = getById(request.getId());
        Utilities.updateProperties(request, productAttribute);
        attributeRepository.save(productAttribute);
        return Utilities.copyProperties(productAttribute, ProductAttributeResponse.class);
    }

    @Override
    public Boolean delete(Long id) {
        try {
            attributeRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleteAll(List<Long> ids) {
        try {
            List<ProductAttribute> attributeList = attributeRepository.findAll();
            if (attributeList.size() == ids.size()) {
                throw new BusinessException(MessageCode.ERR_404.name(), messageUtil.getMessage(MessageCode.BASE_04001.name()), "Product Attribute ID: " + ids);
            }
            attributeRepository.deleteAll(attributeList);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
