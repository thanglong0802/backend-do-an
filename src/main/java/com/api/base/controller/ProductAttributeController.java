package com.api.base.controller;

import com.api.base.domain.PagingRequest;
import com.api.base.domain.PagingResponse;
import com.api.base.domain.productattribute.ProductAttributeCreateRequest;
import com.api.base.domain.productattribute.ProductAttributeRequest;
import com.api.base.domain.productattribute.ProductAttributeResponse;
import com.api.base.domain.productattribute.ProductAttributeUpdateRequest;
import com.api.base.service.ProductAttributeService;
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

@Api(description = "Model Product Attribute")
@RestController
@RequestMapping("/product-attribute")
public class ProductAttributeController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ProductAttributeService productAttributeService;

    public ProductAttributeController(ProductAttributeService productAttributeService) {
        this.productAttributeService = productAttributeService;
    }

    @ApiOperation(value = "Get list Product Attribute")
    @GetMapping
    public ResponseEntity<PagingResponse> search(ProductAttributeRequest request, PagingRequest pagingRequest) {
        logger.info("[Filters] {}", request);
        Pageable pageable = PageRequest.of(pagingRequest.getOffset(), pagingRequest.getLimit(), pagingRequest.getSort(Sort.by(Sort.Direction.DESC, "name")));
        return ResponseEntity.ok(productAttributeService.search(request, pageable));
    }

    @ApiOperation("Create new Product Attribute")
    @PostMapping
    public ResponseEntity<ProductAttributeResponse> insert(@Valid @RequestBody ProductAttributeCreateRequest request) {
        logger.info("[Create new] {}", request);
        return ResponseEntity.ok(productAttributeService.insert(request));
    }

    @ApiOperation("Get detail Product Attribute By ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductAttributeResponse> detail(@PathVariable Long id) {
        logger.info("[Get detail product attribute by ID] {}");
        return ResponseEntity.ok(productAttributeService.detail(id));
    }

    @ApiOperation("Update Product Attribute")
    @PutMapping
    public ResponseEntity<ProductAttributeResponse> update(@Valid @RequestBody ProductAttributeUpdateRequest request) {
        logger.info("[Update] {}");
        return ResponseEntity.ok(productAttributeService.update(request));
    }

    @ApiOperation("Delete product attribute by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        logger.info("[Delete Product attribute by ID] {}");
        return ResponseEntity.ok(productAttributeService.delete(id));
    }

    @ApiOperation("Delete product attribute by list ID")
    @DeleteMapping
    public ResponseEntity<Boolean> deleteAll(@RequestParam List<Long> ids) {
        logger.info("[Delete product attribute by list id] {}");
        return ResponseEntity.ok(productAttributeService.deleteAll(ids));
    }
}
