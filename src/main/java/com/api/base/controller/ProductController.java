package com.api.base.controller;

import com.api.base.domain.PagingRequest;
import com.api.base.domain.PagingResponse;
import com.api.base.domain.product.*;
import com.api.base.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "Module Product")
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "Tìm kiếm sản phẩm theo tên, nếu không truyền tham số thì lấy ra tất cả")
    @GetMapping
    public ResponseEntity<PagingResponse> search(ProductRequest request, PagingRequest pagingRequest) {
        logger.info("[Filters] {}", request);
        Pageable pageable = PageRequest.of(pagingRequest.getOffset(), pagingRequest.getLimit(), pagingRequest.getSort(Sort.by(Sort.Direction.DESC, "name_product")));
        return ResponseEntity.ok(productService.search(request, pageable));
    }

    @ApiOperation(value = "Tạo mới sản phẩm")
    @PostMapping
    public ResponseEntity<ProductResponse> insert(@Valid @RequestBody ProductCreateRequest request) {
        logger.info("[Create new] {}", request);
        return ResponseEntity.ok(productService.insert(request));
    }

    @ApiOperation(value = "Cập nhật sản phẩm")
    @PutMapping
    public ResponseEntity<ProductResponse> update(@Valid @RequestBody ProductUpdateRequest request) {
        logger.info("[Update] {}");
        return ResponseEntity.ok(productService.update(request));
    }

    @ApiOperation(value = "Chi tiết sản phẩm theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> detail(@PathVariable Long id) {
        logger.info("[Get by ID] {}");
        return ResponseEntity.ok(productService.detail(id));
    }

    @ApiOperation(value = "Xóa sản phẩm theo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        logger.info("[Delete by ID] {}");
        return ResponseEntity.ok(productService.delete(id));
    }

    @ApiOperation(value = "Xóa sản phẩm theo danh sách iD")
    @DeleteMapping
    public ResponseEntity<Boolean> deleteAll(@RequestParam List<Long> ids) {
        logger.info("[Delete by quantity] {}");
        return ResponseEntity.ok(productService.deleteAll(ids));
    }
}
