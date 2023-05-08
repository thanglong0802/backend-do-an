package com.api.base.service.impl;

import com.api.base.domain.productdetail.*;
import com.api.base.entity.ProductDetail;
import com.api.base.exception.BusinessException;
import com.api.base.repository.ProductDetailRepository;
import com.api.base.service.CommonService;
import com.api.base.service.ProductDetailService;
import com.api.base.utils.MessageUtil;
import com.api.base.utils.Utilities;
import com.api.base.utils.enumerate.MessageCode;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailRepository productDetailRepository;
    private final MessageUtil messageUtil;
    private final CommonService commonService;

    public ProductDetailServiceImpl(ProductDetailRepository productDetailRepository, MessageUtil messageUtil, CommonService commonService) {
        this.productDetailRepository = productDetailRepository;
        this.messageUtil = messageUtil;
        this.commonService = commonService;
    }

    @Override
    public List<ProductDetailDetailResponse> detail() {
        List<ProductDetail> productDetail = productDetailRepository.findAll();
        return Utilities.copyProperties(productDetail, ProductDetailDetailResponse.class);
    }

    @Override
    public ProductDetailResponse insert(ProductDetailCreateRequest request) {
        ProductDetail productDetail = Utilities.copyProperties(request, ProductDetail.class);
        productDetailRepository.save(productDetail);
        return Utilities.copyProperties(productDetail, ProductDetailResponse.class);
    }

    @Override
    public ProductDetailResponse update(ProductDetailUpdateRequest request) {
        ProductDetail productDetail = Utilities.returnNullInException(() -> productDetailRepository.findById(request.getId()).get());
        if (ObjectUtils.allNull(productDetail)) {
            throw new BusinessException(MessageCode.ERR_404.name(), messageUtil.getMessage(MessageCode.BASE_06001.name()), "product detail id: " + request.getId());
        }
        Utilities.updateProperties(request, productDetail);
        productDetailRepository.save(productDetail);
        return Utilities.copyProperties(productDetail, ProductDetailResponse.class);
    }

    @Override
    public Boolean delete(Long id) {
        try {
            productDetailRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<ProductDetailResponseWithAttribute> searchProductByCategoryAndAttribute() {
        List<ProductDetailResponseWithAttribute> result = new ArrayList<>();
        String sql = " SELECT d.id, c.name_category, a.name_attribute, v.name_value"
                        + " FROM tbl_product_detail_attribute d"
                        + "     INNER JOIN tbl_category c ON d.category_id = c.id"
                        + "     INNER JOIN tbl_product_attribute a ON d.attribute_id = a.id"
                        + "     INNER JOIN tbl_product_value v ON d.attribute_value = v.id";
        Map<String, Object> params = new HashMap<>();
        List<Tuple> tuples = commonService.executeGetListTuple(sql, params);
        for (Tuple item : tuples) {
            ProductDetailResponseWithAttribute attribute = new ProductDetailResponseWithAttribute();
            attribute.setId(Utilities.returnNullInException(() -> item.get("id", BigInteger.class).longValue()));
            attribute.setNameAttribute(item.get("name_attribute", String.class));
            attribute.setNameCategory(item.get("name_category", String.class));
            attribute.setNameValue(item.get("name_value", String.class));
            result.add(attribute);
        }
        return result;
    }

}
