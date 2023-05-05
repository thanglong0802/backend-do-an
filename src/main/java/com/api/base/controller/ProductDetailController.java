package com.api.base.controller;

import com.api.base.domain.productdetail.ProductDetailCreateRequest;
import com.api.base.domain.productdetail.ProductDetailDetailResponse;
import com.api.base.domain.productdetail.ProductDetailResponse;
import com.api.base.domain.productdetail.ProductDetailUpdateRequest;
import com.api.base.service.ProductDetailService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product-detail")
@Api(description = "API product detail")
public class ProductDetailController {

    private final ProductDetailService productDetailService;

    public ProductDetailController(ProductDetailService productDetailService) {
        this.productDetailService = productDetailService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDetailDetailResponse>> detail() {
        return ResponseEntity.ok(productDetailService.detail());
    }

    @PostMapping
    public ResponseEntity<ProductDetailResponse> insert(@Valid @RequestBody ProductDetailCreateRequest request) {
        return ResponseEntity.ok(productDetailService.insert(request));
    }

    @PutMapping
    public ResponseEntity<ProductDetailResponse> update(@Valid @RequestBody ProductDetailUpdateRequest request) {
        return ResponseEntity.ok(productDetailService.update(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(productDetailService.delete(id));
    }
}
