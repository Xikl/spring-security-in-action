package com.ximo.spring.security.sdk.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author 朱文赵
 * @date 2018/7/1
 * @description
 */
@Component
public class SmsValidateCodeAuthenticationSecurityConfig extends
        SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Qualifier("userServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) {
        //配置过滤器
        SmsValidateCodeAuthenticationFilter smsValidateCodeAuthenticationFilter =
                new SmsValidateCodeAuthenticationFilter();
        smsValidateCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsValidateCodeAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        smsValidateCodeAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        //配置提供者
        SmsValidateCodeAuthenticationProvider smsValidateCodeAuthenticationProvider =
                new SmsValidateCodeAuthenticationProvider();
        smsValidateCodeAuthenticationProvider.setUserDetailsService(userDetailsService);

        //加入spring-security中
        http.authenticationProvider(smsValidateCodeAuthenticationProvider)
                .addFilterAfter(smsValidateCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
