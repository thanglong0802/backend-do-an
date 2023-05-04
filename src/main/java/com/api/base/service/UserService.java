package com.api.base.service;

import com.api.base.domain.PagingResponse;
import com.api.base.domain.user.UserCreateRequest;
import com.api.base.domain.user.UserRequest;
import com.api.base.domain.user.UserResponse;
import com.api.base.domain.user.UserUpdateRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    public UserResponse insert(UserCreateRequest request);
    PagingResponse search(UserRequest request, Pageable pageRequest);
    UserResponse update(UserUpdateRequest request);
    Boolean delete(String userName);
    Boolean deleteAll(List<String> userName);
}
