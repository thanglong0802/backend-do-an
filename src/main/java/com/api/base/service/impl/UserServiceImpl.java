package com.api.base.service.impl;

import com.api.base.domain.PagingResponse;
import com.api.base.domain.user.UserCreateRequest;
import com.api.base.domain.user.UserRequest;
import com.api.base.domain.user.UserResponse;
import com.api.base.domain.user.UserUpdateRequest;
import com.api.base.entity.User;
import com.api.base.exception.BusinessException;
import com.api.base.repository.UserRepository;
import com.api.base.service.CommonService;
import com.api.base.service.UserService;
import com.api.base.utils.MessageUtil;
import com.api.base.utils.SimpleQueryBuilder;
import com.api.base.utils.Utilities;
import com.api.base.utils.enumerate.MessageCode;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CommonService commonService;
    private final MessageUtil messageUtil;

    public UserServiceImpl(UserRepository userRepository, CommonService commonService, MessageUtil messageUtil) {
        this.userRepository = userRepository;
        this.commonService = commonService;
        this.messageUtil = messageUtil;
    }

    @Override
    public UserResponse insert(UserCreateRequest request) {
        User user = Utilities.copyProperties(request, User.class);
        userRepository.save(user);
        return Utilities.copyProperties(user, UserResponse.class);
    }

    @Override
    public PagingResponse search(UserRequest request, Pageable pageRequest) {
        StringBuilder whereClause = new StringBuilder("1 = 1");
        SimpleQueryBuilder simpleQueryBuilder = new SimpleQueryBuilder();
        Map<String, Object> params = new HashMap<>();
        whereClause.append(Utilities.buildWhereClause(request, params));

        simpleQueryBuilder.from("tbl_user");
        simpleQueryBuilder.where(whereClause.toString());

        PagingResponse pagingRs = commonService.executeSearchData(pageRequest, simpleQueryBuilder, params, User.class);
        List<User> datas = (List<User>) pagingRs.getData();
        List<UserResponse> caseResponses = Utilities.copyProperties(datas, UserResponse.class);
        pagingRs.setData(caseResponses);
        return pagingRs;
    }

    @Override
    public UserResponse update(UserUpdateRequest request) {
        User user = userRepository.findUserByUsername(request.getUsername());
        Utilities.updateProperties(request, user);
        userRepository.save(user);
        return Utilities.copyProperties(user, UserResponse.class);
    }

    @Override
    public Boolean delete(String userName) {
        try {
            User user = userRepository.findUserByUsername(userName);
            if (ObjectUtils.allNull(user)) {
                throw new BusinessException(MessageCode.ERR_404.name(), messageUtil.getMessage(MessageCode.BASE_07001.name()), "user name not found: " + userName);
            }
            userRepository.deleteUserByUsername(userName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean deleteAll(List<String> userName) {
        try {
            for (String name: userName) {
                User user = userRepository.findUserByUsername(name);
                userRepository.delete(user);
            }
        }catch (Exception e) {
            return false;
        }
        return true;
    }
}
