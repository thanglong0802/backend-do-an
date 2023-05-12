package com.api.base.controller;

import com.api.base.domain.PagingRequest;
import com.api.base.domain.PagingResponse;
import com.api.base.domain.productvalue.ProductValueCreateRequest;
import com.api.base.domain.productvalue.ProductValueRequest;
import com.api.base.domain.productvalue.ProductValueResponse;
import com.api.base.domain.productvalue.ProductValueUpdateRequest;
import com.api.base.service.ProductValueService;
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

@Api(description = "Module Product Value")
@RestController
@RequestMapping("/product-value")
public class ProductValueController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ProductValueService productValueService;

    public ProductValueController(ProductValueService productValueService) {
        this.productValueService = productValueService;
    }

    @ApiOperation("Create new Product Value")
    @PostMapping
    public ResponseEntity<ProductValueResponse> insert(@Valid @RequestBody ProductValueCreateRequest request) {
        logger.info("[Create new] {}", request);
        return ResponseEntity.ok(productValueService.insert(request));
    }

    @ApiOperation("Update product value")
    @PutMapping
    public ResponseEntity<ProductValueResponse> update(@Valid @RequestBody ProductValueUpdateRequest request) {
        logger.info("[Update product value] {}");
        return ResponseEntity.ok(productValueService.update(request));
    }

    @ApiOperation("Get list product value")
    @GetMapping
    public ResponseEntity<PagingResponse> search(ProductValueRequest request, PagingRequest pagingRequest) {
        logger.info("Filters] {}");
        Pageable pageable = PageRequest.of(pagingRequest.getOffset(), pagingRequest.getLimit(), pagingRequest.getSort(Sort.by(Sort.Direction.ASC, "name_value")));
        return ResponseEntity.ok(productValueService.search(request, pageable));
    }

    @ApiOperation("Delete product value by ID")
    @DeleteMapping("/{id}")
    public  ResponseEntity<Boolean> delete(@PathVariable Long id) {
        logger.info("[Delete Product value by ID] {}");
        return ResponseEntity.ok(productValueService.delete(id));
    }

    @ApiOperation("Delete product value by list ID")
    @DeleteMapping
    public ResponseEntity<Boolean> deleteAll(@RequestParam List<Long> ids) {
        logger.info("[Delete product value by list ID] {}");
        return ResponseEntity.ok(productValueService.deleteAll(ids));
    }

}
