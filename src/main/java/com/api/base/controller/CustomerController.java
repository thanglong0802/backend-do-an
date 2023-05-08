package com.api.base.controller;

import com.api.base.domain.PagingRequest;
import com.api.base.domain.PagingResponse;
import com.api.base.domain.customer.CustomerCreateRequest;
import com.api.base.domain.customer.CustomerRequest;
import com.api.base.domain.customer.CustomerResponse;
import com.api.base.domain.customer.CustomerUpdateRequest;
import com.api.base.service.CustomerService;
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

@RestController
@RequestMapping("/customer")
@Api(description = "Module Customer")
public class CustomerController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "Get list Customer")
    @GetMapping
    public ResponseEntity<PagingResponse> search(CustomerRequest request, PagingRequest pagingRequest) {
        logger.info("[Filters] {}", request);
        Pageable pageRequest = PageRequest.of(pagingRequest.getOffset(), pagingRequest.getLimit(), pagingRequest.getSort(Sort.by(Sort.Direction.ASC, "phone_number")));
        return ResponseEntity.ok(customerService.search(request, pageRequest));
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> insert(@Valid @RequestBody CustomerCreateRequest request) {
        logger.info("[Create new customer] {}");
        return ResponseEntity.ok(customerService.insert(request));
    }

    @PutMapping
    public ResponseEntity<CustomerResponse> update(@Valid @RequestBody CustomerUpdateRequest request) {
        logger.info("[Update customer] {}");
        return ResponseEntity.ok(customerService.update(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        logger.info("[Delete customer] {}");
        return ResponseEntity.ok(customerService.delete(id));
    }
}
