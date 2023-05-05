package com.api.base.controller;

import com.api.base.domain.PagingRequest;
import com.api.base.domain.PagingResponse;
import com.api.base.domain.user.UserCreateRequest;
import com.api.base.domain.user.UserRequest;
import com.api.base.domain.user.UserResponse;
import com.api.base.domain.user.UserUpdateRequest;
import com.api.base.service.UserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@Api(description = "Module User")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<PagingResponse> search(UserRequest request, PagingRequest pagingRequest) {
        logger.info("[Filters] {}", request);
        Pageable pageRequest = PageRequest.of(pagingRequest.getOffset(), pagingRequest.getLimit(), pagingRequest.getSort(Sort.by(Sort.Direction.ASC, "user_name")));
        return ResponseEntity.ok(userService.search(request, pageRequest));
    }

    @PostMapping
    public ResponseEntity<UserResponse> insert(@Valid @RequestBody UserCreateRequest request) {
        return ResponseEntity.ok(userService.insert(request));
    }

    @PutMapping
    public ResponseEntity<UserResponse> update(@Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.update(request));
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<Boolean> delete(@PathVariable String userName) {
        return ResponseEntity.ok(userService.delete(userName));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteAll(@RequestParam List<String> userName) {
        return ResponseEntity.ok(userService.deleteAll(userName));
    }
}
