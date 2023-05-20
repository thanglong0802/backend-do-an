package com.api.base.controller;

import com.api.base.domain.productdetail.*;
import com.api.base.service.ProductDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product-detail")
@Api(description = "Module product detail")
public class ProductDetailController {

    private final ProductDetailService productDetailService;

    public ProductDetailController(ProductDetailService productDetailService) {
        this.productDetailService = productDetailService;
    }

    @ApiOperation(value = "Lấy ra chi tiết sản phẩm của bảng tiết sản phẩm")
    @GetMapping
    public ResponseEntity<List<ProductDetailDetailResponse>> detail() {
        return ResponseEntity.ok(productDetailService.detail());
    }

    @ApiOperation(value = "Danh sách sản phẩm theo danh mục thuộc tính")
    @GetMapping("/category-attribute")
    public List<ProductDetailResponseWithAttribute> categoryAttribute() {
        return productDetailService.searchProductByCategoryAndAttribute();
    }

    @ApiOperation(value = "Danh sách thuộc tính của sản phẩm")
    @GetMapping("/{id}")
    public List<ProductDetailAttributeOfProductResponse> attributeOfProductResponseList(@PathVariable Long id) {
        return productDetailService.attributeOfProduct(id);
    }

    @ApiOperation(value = "Tạo mới chi tiết sản phẩm")
    @PostMapping
    public ResponseEntity<ProductDetailResponse> insert(@Valid @RequestBody ProductDetailCreateRequest request) {
        return ResponseEntity.ok(productDetailService.insert(request));
    }

    @ApiOperation(value = "Cập nhật chi tiết sản phẩm")
    @PutMapping
    public ResponseEntity<ProductDetailResponse> update(@Valid @RequestBody ProductDetailUpdateRequest request) {
        return ResponseEntity.ok(productDetailService.update(request));
    }

    @ApiOperation(value = "Xóa chi tiết sản phẩm theo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(productDetailService.delete(id));
    }
}
