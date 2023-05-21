package com.api.base.controller;

import com.api.base.domain.productimage.ProductImageCreateRequest;
import com.api.base.domain.productimage.ProductImageResponse;
import com.api.base.domain.productimage.ProductImageUpdateRequest;
import com.api.base.service.ProductImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "Module Product Image")
@RestController
@RequestMapping("/product-image")
public class ProductImageController {
    private final ProductImageService productImageService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @ApiOperation(value = "Lấy danh sách ảnh")
    @GetMapping
    public ResponseEntity<List<ProductImageResponse>> getAll() {
        return ResponseEntity.ok(productImageService.getAll());
    }

    @ApiOperation(value = "Lấy ảnh theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<List<ProductImageResponse>> findByProductId(@PathVariable Long id) {
        return ResponseEntity.ok(productImageService.findByProductId(id));
    }

    @ApiOperation(value = "Tạo mới ảnh cho sản phẩm")
    @PostMapping
    public ResponseEntity<ProductImageResponse> insert(@Valid @RequestBody ProductImageCreateRequest request) {
        logger.info("[Create new] {}", request);
        return ResponseEntity.ok(productImageService.insert(request));
    }

    @ApiOperation(value = "Cập nhật lại ảnh cho sản phẩm")
    @PutMapping
    public ResponseEntity<ProductImageResponse> update(@Valid @RequestBody ProductImageUpdateRequest request) {
        logger.info("[Update] {}", request);
        return ResponseEntity.ok(productImageService.update(request));
    }

    @ApiOperation(value = "Xóa ảnh theo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        logger.info("[Delete by ID] {}");
        return ResponseEntity.ok(productImageService.delete(id));
    }

    @ApiOperation(value = "Xóa ảnh theo danh sách ID")
    @DeleteMapping
    public ResponseEntity<Boolean> deleteAll(@RequestParam List<Long> ids) {
        logger.info("[Delete ID list] {}");
        return ResponseEntity.ok(productImageService.deleteAll(ids));
    }
}
