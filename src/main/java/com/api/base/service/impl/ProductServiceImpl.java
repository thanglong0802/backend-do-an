package com.api.base.service.impl;

import com.api.base.domain.PagingResponse;
import com.api.base.domain.product.*;
import com.api.base.entity.Category;
import com.api.base.entity.Product;
import com.api.base.exception.BusinessException;
import com.api.base.repository.CategoryRepository;
import com.api.base.repository.ProductRepository;
import com.api.base.service.CommonService;
import com.api.base.service.ProductService;
import com.api.base.utils.MessageUtil;
import com.api.base.utils.SimpleQueryBuilder;
import com.api.base.utils.Utilities;
import com.api.base.utils.enumerate.MessageCode;
import com.api.base.utils.enumerate.ProductStatus;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final MessageUtil messageUtil;
    private final CommonService commonService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, MessageUtil messageUtil, CommonService commonService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.messageUtil = messageUtil;
        this.commonService = commonService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PagingResponse search(ProductRequest request, Pageable pageable) {
        StringBuilder whereClause = new StringBuilder("1 = 1");
        SimpleQueryBuilder simpleQueryBuilder = new SimpleQueryBuilder();
        Map<String, Object> params = new HashMap<>();
        whereClause.append(Utilities.buildWhereClause(request, params));

        simpleQueryBuilder.from("tbl_product");
        simpleQueryBuilder.where(whereClause.toString());

        PagingResponse pagingResponse = commonService.executeSearchData(pageable, simpleQueryBuilder, params, Product.class);
        List<Product> productList = (List<Product>) pagingResponse.getData();
        List<ProductResponse> productResponses = Utilities.copyProperties(productList, ProductResponse.class);
        pagingResponse.setData(productResponses);
        return pagingResponse;
    }

    @Override
    public ProductResponse insert(ProductCreateRequest request) {
        // check tồn tại Category
        Category category = Utilities.returnNullInException(() -> categoryRepository.findById(request.getCategoriesId()).get());
        if(ObjectUtils.allNull(category)) {
            throw new BusinessException(MessageCode.ERR_404.name(), messageUtil.getMessage(MessageCode.BASE_01001.name()), "Category id: " + request.getCategoriesId());
        }
        // thêm mới sản phẩm
        Product product = Utilities.copyProperties(request, Product.class);
        product.setStatus(ProductStatus.CON_HANG.getValue());
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
