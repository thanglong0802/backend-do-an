package com.api.base.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.base.utils.Utilities;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.api.base.utils.enumerate.MessageCode;

@Component
public class AuthenticationEntry implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        Utilities.buildErrorResponse(response, MessageCode.ERR_401.name(), HttpStatus.UNAUTHORIZED.value(), "Unauthorized", null);
    }

}