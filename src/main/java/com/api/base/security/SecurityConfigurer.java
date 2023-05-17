package com.api.base.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.base.filter.AppFilter;

public class SecurityConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private TokenProvider tokenProvider;

    public SecurityConfigurer(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AppFilter customFilter = new AppFilter(tokenProvider);

        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntry()).and().addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}