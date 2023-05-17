package com.api.base.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.api.base.security.AuthenticationEntry;
import com.api.base.security.SecurityConfigurer;
import com.api.base.security.TokenProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final List<String> DEFAULT_PERMIT_METHODS = Collections.unmodifiableList(
            Arrays.asList(HttpMethod.GET.name(), HttpMethod.HEAD.name(), HttpMethod.POST.name(), HttpMethod.DELETE.name(), HttpMethod.PUT.name(), HttpMethod.OPTIONS.name()));

    @Autowired
    TokenProvider tokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    AuthenticationEntry authenticationEntry;
    
    private static final String[] WHITE_LIST = {
            "/resources/**", 
            "/v2/api-docs", 
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security", 
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource());
        http.httpBasic()
                .disable().csrf()
                .disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers("/ping").permitAll()
                .anyRequest().permitAll()
                //.anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(authenticationEntry)
                .and().apply(new SecurityConfigurer(tokenProvider));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(WHITE_LIST);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(DEFAULT_PERMIT_METHODS);
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList(
                "Authorization",
                "Content-Length",
                "Cache-Control",
                "Content-Type",
                "Content-Disposition",
                "Content-Transfer-Encoding",
                "Access-Control-Expose-Headers",
                "Access-Control-Allow-Headers"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
