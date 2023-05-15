package com.api.base.controller;

import com.api.base.domain.PagingRequest;
import com.api.base.domain.PagingResponse;
import com.api.base.domain.user.*;
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

    @PostMapping("/login")
    public ResponseEntity<UserLogin> login(@Valid @RequestBody UserLogin login) {
        return ResponseEntity.ok(userService.login(login));
    }

    @PostMapping
    public ResponseEntity<UserResponse> insert(@Valid @RequestBody UserCreateRequest request) {
        logger.info("[Create new] {}");
        return ResponseEntity.ok(userService.insert(request));
    }

    @PutMapping
    public ResponseEntity<UserResponse> update(@Valid @RequestBody UserUpdateRequest request) {
        logger.info("[Update] {}");
        return ResponseEntity.ok(userService.update(request));
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<Boolean> delete(@PathVariable String userName) {
        logger.info("[Delete by user name] {}");
        return ResponseEntity.ok(userService.delete(userName));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteAll(@RequestParam List<String> userName) {
        logger.info("[Delete all user in param] {}");
        return ResponseEntity.ok(userService.deleteAll(userName));
    }
}
