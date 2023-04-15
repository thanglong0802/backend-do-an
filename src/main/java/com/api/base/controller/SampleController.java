package com.api.base.controller;

import com.api.base.domain.PagingRequest;
import com.api.base.domain.PagingResponse;
import com.api.base.domain.sample.SampleCreateRequest;
import com.api.base.domain.sample.SampleDetailResponse;
import com.api.base.domain.sample.SampleRequest;
import com.api.base.domain.sample.SampleResponse;
import com.api.base.domain.sample.SampleUpdateRequest;
import com.api.base.service.SampleService;
import com.api.base.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.util.List;

@Api(description = "Module Sample")
@RestController
@RequestMapping("/sample")
public class SampleController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final SampleService sampleService;

    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @ApiOperation(value = "Get list Sample")
    @GetMapping
    public ResponseEntity<PagingResponse> search(SampleRequest request, PagingRequest pagingRequest) {
        logger.info("[Filters] {}", request);
        Pageable pageRequest = PageRequest.of(pagingRequest.getOffset(), pagingRequest.getLimit(), pagingRequest.getSort(Sort.by(Sort.Direction.ASC, "name")));
        return ResponseEntity.ok(sampleService.search(request, pageRequest));
    }

    @ApiOperation(value = "Create new Sample")
    @PostMapping
    public ResponseEntity<SampleResponse> insert(@Valid @RequestBody SampleCreateRequest request) {
        logger.info("[Create] {}", request);
        return ResponseEntity.ok(sampleService.insert(request));
    }

    @ApiOperation(value = "Update Sample")
    @PutMapping
    public ResponseEntity<SampleResponse> update(@Valid @RequestBody SampleUpdateRequest request) {
        logger.info("[Update] {}", request);
        return ResponseEntity.ok(sampleService.update(request));
    }

    @ApiOperation(value = "Get detail Sample by id")
    @GetMapping("/{id}")
    public ResponseEntity<SampleDetailResponse> detail(@PathVariable("id") Long id) {
        logger.info("[Get detail by ID] {}", id);
        return ResponseEntity.ok(sampleService.detail(id));
    }

    @ApiOperation(value = "Delete Sample by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        logger.info("[Delete by ID] {}", id);
        return ResponseEntity.ok(sampleService.delete(id));
    }

    @ApiOperation(value = "Delete all Sample by list ids")
    @DeleteMapping()
    public ResponseEntity<Boolean> deleteAll(@RequestParam("ids") List<Long> ids) {
        logger.info("[Delete by IDs] {}", ids);
        return ResponseEntity.ok(sampleService.deleteAll(ids));
    }

    @ApiOperation(value = "Export Data Sample")
    @GetMapping("/export")
    public void export(SampleRequest request, HttpServletRequest servletRequest, HttpServletResponse response, PagingRequest pagingRequest) throws Exception {
        logger.info("[Export] {}", request);
        Pageable pageRequest = PageRequest.of(0, Integer.MAX_VALUE, pagingRequest.getSort(Sort.by(Sort.Direction.ASC, "name")));
        File file = sampleService.export(request, pageRequest);
        response.setHeader("Content-disposition", "attachment; filename=Report_H01.xlsx");
        FileUtils.exportFile(response, file);
    }
}
