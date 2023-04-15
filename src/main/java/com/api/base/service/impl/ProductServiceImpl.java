package com.api.base.service.impl;

import com.api.base.domain.product.ProductCreateRequest;
import com.api.base.domain.product.ProductDetailResponse;
import com.api.base.domain.product.ProductResponse;
import com.api.base.domain.product.ProductUpdateRequest;
import com.api.base.entity.Category;
import com.api.base.entity.Product;
import com.api.base.exception.BusinessException;
import com.api.base.repository.CategoryRepository;
import com.api.base.repository.ProductRepository;
import com.api.base.service.CategoryService;
import com.api.base.service.ProductService;
import com.api.base.utils.MessageUtil;
import com.api.base.utils.Utilities;
import com.api.base.utils.enumerate.MessageCode;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final MessageUtil messageUtil;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, CategoryRepository categoryRepository, MessageUtil messageUtil) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.messageUtil = messageUtil;
    }

    @Override
    public List<ProductResponse> getAll() {
        List<Product> product = productRepository.findAll();
        return Utilities.copyProperties(product, ProductResponse.class);
    }

    @Override
    public ProductResponse insert(ProductCreateRequest request) {
        // check tồn tại Category
        Category category = Utilities.returnNullInException(() -> categoryRepository.findById(request.getCategoriesId()).get());
        if(ObjectUtils.allNull(category)) {
            throw new BusinessException(MessageCode.ERR_404.name(), messageUtil.getMessage(MessageCode.BASE_02001.name()), "Category id: " + request.getCategoriesId());
        }
        // thêm mới sản phẩm
        Product product = Utilities.copyProperties(request, Product.class);
        productRepository.save(product);
        return Utilities.copyProperties(product, ProductResponse.class);
    }

    // Hàm getID của sản phẩm và kiểm tra xem sản phẩm đã tồn tại trong DB chưa.
    private Product getProductById(Long id) {
        Product product = Utilities.returnNullInException(() -> productRepository.findById(id).get());
        if (ObjectUtils.allNull(product)) {
            throw new BusinessException(MessageCode.ERR_404.name(), messageUtil.getMessage(MessageCode.BASE_02001.name()), "Product id: " + id);
        }
        return product;
    }

    @Override
    public ProductResponse update(ProductUpdateRequest request) {
        Product product = getProductById(request.getId());
        Utilities.updateProperties(request, product);
        productRepository.save(product);
        return Utilities.copyProperties(product, ProductResponse.class);
    }

    @Override
    public ProductDetailResponse detail(Long id) {
        Product product = getProductById(id);
        return Utilities.copyProperties(product, ProductDetailResponse.class);
    }

    @Override
    public Boolean delete(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleteAll(List<Long> ids) {
        try {
            List<Product> product = productRepository.findAllById(ids);
            if (product.size() == ids.size()) {
                productRepository.deleteAll(product);
            } else {
                throw new BusinessException(MessageCode.ERR_404.name(), messageUtil.getMessage(MessageCode.BASE_02001.name()), "Product list id: " + ids);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
