package com.api.base.service.impl;

import com.api.base.domain.PagingResponse;
import com.api.base.domain.user.*;
import com.api.base.entity.User;
import com.api.base.exception.BusinessException;
import com.api.base.repository.UserRepository;
import com.api.base.service.CommonService;
import com.api.base.service.UserService;
import com.api.base.utils.MessageUtil;
import com.api.base.utils.SimpleQueryBuilder;
import com.api.base.utils.Utilities;
import com.api.base.utils.enumerate.MessageCode;
import com.api.base.utils.validator.EmailValidator;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl extends EmailValidator implements UserService {

    private final UserRepository userRepository;
    private final CommonService commonService;
    private final MessageUtil messageUtil;

    public UserServiceImpl(UserRepository userRepository, CommonService commonService, MessageUtil messageUtil) {
        this.userRepository = userRepository;
        this.commonService = commonService;
        this.messageUtil = messageUtil;
    }

    private boolean emailValidation(String email) {
        boolean emailValidator = isValidEmail(email);
        if (!(emailValidator == true)) {
            throw new BusinessException("Incorrect email format");
        }
        return true;
    }

    private boolean passwordValidation(String password) {
        if (isAscii(password)) {
            Pattern pattern = Pattern.compile("^(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$");
            Matcher matcher = pattern.matcher(password);
            if (!matcher.matches()) {
                throw new BusinessException("Incorrect password format");
            }
            return true;
        } else {
            throw new BusinessException("characters not in ascii");
        }
    }

    @Override
    public UserResponse insert(UserCreateRequest request) {
        emailValidation(request.getEmail());
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
    public UserLogin login(UserLogin login) {
        User userNameLogin = userRepository.findUserByUsername(login.getUsername());
        if (ObjectUtils.allNull(userNameLogin)) {
            throw new BusinessException("User Not Found");
        }
        String passwordUser = userNameLogin.getPassword();
        if (!passwordUser.equals(login.getPassword())) {
            throw new BusinessException("Password Not Found");
        }
        return Utilities.copyProperties(userNameLogin, UserLogin.class);
    }

    @Override
    public UserResponse update(UserUpdateRequest request) {
        User user = userRepository.findUserByUsername(request.getUsername());
        emailValidation(request.getEmail());
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
