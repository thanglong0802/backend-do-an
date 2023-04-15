package com.api.base.controller;

import com.api.base.domain.product.ProductCreateRequest;
import com.api.base.domain.product.ProductDetailResponse;
import com.api.base.domain.product.ProductResponse;
import com.api.base.domain.product.ProductUpdateRequest;
import com.api.base.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "Model Product")
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "Get all")
    @GetMapping
    public List<ProductResponse> getAll() {
        return productService.getAll();
    }

    @ApiOperation(value = "Create new")
    @PostMapping
    public ResponseEntity<ProductResponse> insert(@Valid @RequestBody ProductCreateRequest request) {
        return ResponseEntity.ok(productService.insert(request));
    }

    @ApiOperation(value = "Update")
    @PutMapping
    public ResponseEntity<ProductResponse> update(@Valid @RequestBody ProductUpdateRequest request) {
        return ResponseEntity.ok(productService.update(request));
    }

    @ApiOperation(value = "Get by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> detail(@PathVariable Long id) {
        return ResponseEntity.ok(productService.detail(id));
    }

    @ApiOperation(value = "Delete by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(productService.delete(id));
    }

    @ApiOperation(value = "Delete by list")
    @DeleteMapping
    public ResponseEntity<Boolean> deleteAll(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(productService.deleteAll(ids));
    }
}
