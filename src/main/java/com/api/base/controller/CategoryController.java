package com.api.base.controller;

import com.api.base.domain.PagingRequest;
import com.api.base.domain.PagingResponse;
import com.api.base.domain.category.*;
import com.api.base.service.CategoryService;
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

@Api(description = "Module Category")
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "Tìm kiếm danh mục theo tên, nếu không truyền tham số sẽ hiển thị tất cả danh mục")
    @GetMapping("/search")
    public ResponseEntity<PagingResponse> search(CategoryRequest categoryRequest, PagingRequest pagingRequest) {
        logger.info("[Filter] {}", categoryRequest);
        Pageable pageable = PageRequest.of(pagingRequest.getOffset(), pagingRequest.getLimit(), pagingRequest.getSort(Sort.by(Sort.Direction.DESC, "name_category")));
        return ResponseEntity.ok(categoryService.search(categoryRequest, pageable));
    }

    @ApiOperation("Lấy ra tất cả danh mục cha")
    @GetMapping
    public List<CategoryResponse> getAll() {
        return categoryService.getParentCategory();
    }

    @ApiOperation("Lấy ra tất cả sản phẩm của danh mục đó")
    @GetMapping("/all-product/{id}")
    public List<CategoryResponseProduct> getAllProductsInTheCategory(@PathVariable Long id) {
        return categoryService.getAllProductsInTheCategory(id);
    }

    @ApiOperation("Lấy ra tất cả danh mục con theo danh mục cha")
    @GetMapping("/{id}")
    public List<CategoryResponse> directoryList(@PathVariable Long id) {
        return categoryService.directoryList(id);
    }

    @ApiOperation(value = "Tạo mới danh mục")
    @PostMapping
    public ResponseEntity<CategoryResponse> insert(@Valid @RequestBody CategoryCreateRequest request) {
        logger.info("[Create new] {}", request);
        return ResponseEntity.ok(categoryService.insert(request));
    }

    @ApiOperation(value = "Lấy ra chi tiết của danh mục")
    @GetMapping("/detail/{id}")
    public ResponseEntity<CategoryDetailResponse> detail(@PathVariable Long id) {
        logger.info("[Get by ID] {}");
        return ResponseEntity.ok(categoryService.detail(id));
    }

    @ApiOperation(value = "Cập nhật danh mục")
    @PutMapping
    public ResponseEntity<CategoryResponse> update(@Valid @RequestBody CategoryUpdateRequest request) {
        logger.info("[Update] {}");
        return ResponseEntity.ok(categoryService.update(request));
    }

    @ApiOperation(value = "Xóa danh mục theo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        logger.info("[Delete by ID] {}");
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @ApiOperation(value = "Xóa danh mục theo danh sách ID")
    @DeleteMapping
    public ResponseEntity<Boolean> deleteAll(@RequestParam List<Long> ids) {
        logger.info("[Delete by list] {}");
        return ResponseEntity.ok(categoryService.deleteAll(ids));
    }

}
