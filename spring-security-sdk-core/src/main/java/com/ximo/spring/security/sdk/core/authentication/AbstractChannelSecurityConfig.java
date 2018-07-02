package com.ximo.spring.security.sdk.core.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static com.ximo.spring.security.sdk.core.constants.SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM;
import static com.ximo.spring.security.sdk.core.constants.SecurityConstants.DEFAULT_UN_AUTHENTICATION_URL;

/**
 * @author 朱文赵
 * @date 2018/7/2
 * @description 密码相关配置
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 引用密码校验
     *
     * @param http HttpSecurity
     * @throws Exception Exception
     */
    protected void applyPasswordAuthenticationConfig(HttpSecurity http)  throws Exception {
        http
                .formLogin()
                .loginPage(DEFAULT_UN_AUTHENTICATION_URL)
                .loginProcessingUrl(DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(authenticationSuccessHandler)
                .failureForwardUrl(DEFAULT_UN_AUTHENTICATION_URL);

    }
}
