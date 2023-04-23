package com.api.base.service.impl;

import com.api.base.domain.PagingResponse;
import com.api.base.domain.productvalue.ProductValueCreateRequest;
import com.api.base.domain.productvalue.ProductValueRequest;
import com.api.base.domain.productvalue.ProductValueResponse;
import com.api.base.domain.productvalue.ProductValueUpdateRequest;
import com.api.base.domain.sample.SampleResponse;
import com.api.base.entity.ProductValue;
import com.api.base.entity.Sample;
import com.api.base.exception.BusinessException;
import com.api.base.repository.ProductValueRepository;
import com.api.base.service.CommonService;
import com.api.base.service.ProductValueService;
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
public class ProductValueServiceImpl implements ProductValueService {
    private final ProductValueRepository valueRepository;
    private final MessageUtil messageUtil;
    private final CommonService commonService;

    public ProductValueServiceImpl(ProductValueRepository valueRepository, MessageUtil messageUtil, CommonService commonService) {
        this.valueRepository = valueRepository;
        this.messageUtil = messageUtil;
        this.commonService = commonService;
    }

    @Override
    public ProductValueResponse insert(ProductValueCreateRequest request) {
        ProductValue productValue = Utilities.copyProperties(request, ProductValue.class);
        valueRepository.save(productValue);
        return Utilities.copyProperties(productValue, ProductValueResponse.class);
    }

    @Override
    public ProductValueResponse update(ProductValueUpdateRequest request) {
        ProductValue productValue = Utilities.returnNullInException(() -> valueRepository.findById(request.getProductAttributeId()).get());
        if (ObjectUtils.allNull(productValue)) {
            throw new BusinessException(MessageCode.ERR_404.name(), messageUtil.getMessage(MessageCode.BASE_05001.name()), "Product Attribute ID: " + request.getProductAttributeId());
        }
        Utilities.updateProperties(request, productValue);
        valueRepository.save(productValue);
        return Utilities.copyProperties(productValue, ProductValueResponse.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PagingResponse search(ProductValueRequest request, Pageable pageable) {
        StringBuilder whereClause = new StringBuilder("1 = 1");
        SimpleQueryBuilder simpleQueryBuilder = new SimpleQueryBuilder();
        Map<String, Object> params = new HashMap<>();
        whereClause.append(Utilities.buildWhereClause(request, params));

        simpleQueryBuilder.from("tbl_product_value");
        simpleQueryBuilder.where(whereClause.toString());

        PagingResponse pagingRs = commonService.executeSearchData(pageable, simpleQueryBuilder, params, ProductValue.class);
        List<ProductValue> datas = (List<ProductValue>) pagingRs.getData();
        List<ProductValueResponse> caseResponses = Utilities.copyProperties(datas, ProductValueResponse.class);
        pagingRs.setData(caseResponses);
        return pagingRs;
    }

    @Override
    public Boolean delete(Long id) {
        try {
            valueRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleteAll(List<Long> ids) {
        try {
            List<ProductValue> valueList = valueRepository.findAllById(ids);
            if (ids.size() != valueList.size()) {
                throw new BusinessException(MessageCode.ERR_404.name(), messageUtil.getMessage(MessageCode.BASE_05001.name(), "Product value list ID: " + ids));
            }
            valueRepository.deleteAll(valueList);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
