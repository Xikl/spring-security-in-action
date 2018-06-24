package com.ximo.spring.security.sdk.browser.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author 朱文赵
 * @date 2018/6/24
 * @description
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ACTUATOR")
                .antMatchers("/user/**").authenticated()
                .and()
                .formLogin();
    }
}
