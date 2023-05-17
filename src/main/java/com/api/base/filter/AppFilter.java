package com.api.base.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.base.utils.Utilities;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.base.utils.Constants;
import com.api.base.utils.enumerate.MessageCode;
import com.api.base.security.BearerContextHolder;
import com.api.base.security.TokenProvider;

@Component
public class AppFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AppFilter.class);

    @Autowired
    MessageSource messageSource;

    private TokenProvider tokenProvider;

    public AppFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequestHelper request = new HttpServletRequestHelper(req);

        try {
            String token = tokenProvider.resolveToken(request);

            if (StringUtils.isNotBlank(token)) {
                if (token.startsWith(StringUtils.SPACE)) {
                    Object[] params = {};
                    Utilities.buildErrorResponse(res, MessageCode.ERR_401.name(), HttpStatus.UNAUTHORIZED.value(), messageSource.getMessage(MessageCode.ERR_401.name(), params, LocaleContextHolder.getLocale()), null);
                } else {
                    if (tokenProvider.validateToken(token)) {
                        if (StringUtils.isNotBlank(BearerContextHolder.getContext().getRefreshToken())) {
                            res.setHeader("access_token", BearerContextHolder.getContext().getRefreshToken());
                            token = BearerContextHolder.getContext().getRefreshToken();
                            request.putHeader(Constants.AUTHORIZATION_HEADER, Constants.BEARER_TOKEN_PREFIX + token);
                        }

                        SecurityContextHolder.getContext().setAuthentication(tokenProvider.getAuthentication(token));
                        BearerContextHolder.getContext().setAccessToken(token);
                        BearerContextHolder.getContext().setMemberId(tokenProvider.getMId(token));
                        BearerContextHolder.getContext().setMemberName(tokenProvider.getMemberName(token));

                        filterChain.doFilter(request, res);
                    } else {
                    	Object[] params = {};
                        Utilities.buildErrorResponse(res, MessageCode.ERR_401.name(), HttpStatus.UNAUTHORIZED.value(), messageSource.getMessage(MessageCode.ERR_401.name(), params, LocaleContextHolder.getLocale()), null);
                    }
                }
            } else {
                filterChain.doFilter(request, res);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            Object[] params = {};
            Utilities.buildErrorResponse(res, MessageCode.ERR_400.name(), HttpStatus.BAD_REQUEST.value(), messageSource.getMessage(MessageCode.ERR_400.name(), params, LocaleContextHolder.getLocale()), null);
        } finally {
            BearerContextHolder.clearContext();
        }
    }

}