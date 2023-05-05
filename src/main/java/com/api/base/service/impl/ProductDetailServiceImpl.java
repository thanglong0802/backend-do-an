package com.api.base.service.impl;

import com.api.base.domain.productdetail.ProductDetailCreateRequest;
import com.api.base.domain.productdetail.ProductDetailDetailResponse;
import com.api.base.domain.productdetail.ProductDetailResponse;
import com.api.base.domain.productdetail.ProductDetailUpdateRequest;
import com.api.base.entity.ProductDetail;
import com.api.base.exception.BusinessException;
import com.api.base.repository.ProductDetailRepository;
import com.api.base.service.ProductDetailService;
import com.api.base.utils.MessageUtil;
import com.api.base.utils.Utilities;
import com.api.base.utils.enumerate.MessageCode;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailRepository productDetailRepository;
    private final MessageUtil messageUtil;

    public ProductDetailServiceImpl(ProductDetailRepository productDetailRepository, MessageUtil messageUtil) {
        this.productDetailRepository = productDetailRepository;
        this.messageUtil = messageUtil;
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
}
