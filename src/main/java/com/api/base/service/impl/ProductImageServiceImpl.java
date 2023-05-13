package com.api.base.service.impl;

import com.api.base.domain.productimage.ProductImageCreateRequest;
import com.api.base.domain.productimage.ProductImageResponse;
import com.api.base.domain.productimage.ProductImageUpdateRequest;
import com.api.base.entity.ProductImage;
import com.api.base.exception.BusinessException;
import com.api.base.repository.ProductImageRepository;
import com.api.base.service.ProductImageService;
import com.api.base.utils.MessageUtil;
import com.api.base.utils.Utilities;
import com.api.base.utils.enumerate.MessageCode;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final MessageUtil messageUtil;

    public ProductImageServiceImpl(ProductImageRepository productImageRepository, MessageUtil messageUtil) {
        this.productImageRepository = productImageRepository;
        this.messageUtil = messageUtil;
    }

    @Override
    public List<ProductImageResponse> findByProductId(Long id) {
        List<ProductImage> imageList = productImageRepository.findProductImagesByProductId(id);
        return Utilities.copyProperties(imageList, ProductImageResponse.class);
    }

    @Override
    public ProductImageResponse insert(ProductImageCreateRequest request) {
        ProductImage image = Utilities.copyProperties(request, ProductImage.class);
        productImageRepository.save(image);
        return Utilities.copyProperties(image, ProductImageResponse.class);
    }

    @Override
    public ProductImageResponse update(ProductImageUpdateRequest request) {
        ProductImage productImage = Utilities.returnNullInException(() -> productImageRepository.findById(request.getId()).get());
        if (ObjectUtils.allNull(productImage)) {
            throw new BusinessException(MessageCode.ERR_404.name(), messageUtil.getMessage(MessageCode.BASE_03001.name()), "Product Image id: " + request.getId());
        }
        Utilities.updateProperties(request,  productImage);
        productImageRepository.save(productImage);
        return Utilities.copyProperties(productImage, ProductImageResponse.class);
    }

    @Override
    public Boolean delete(Long id) {
        try {
            productImageRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleteAll(List<Long> ids) {
        try {
            List<ProductImage> imageList = Utilities.returnNullInException(() -> productImageRepository.findAllById(ids));
            if (imageList.size() == ids.size()) {
                productImageRepository.deleteAll(imageList);
            } else {
                throw new BusinessException(MessageCode.ERR_404.name(), messageUtil.getMessage(MessageCode.BASE_03001.name()), "Product image list id: " + ids);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
