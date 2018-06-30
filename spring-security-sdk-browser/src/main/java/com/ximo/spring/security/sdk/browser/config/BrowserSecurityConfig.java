package com.ximo.spring.security.sdk.browser.config;

import com.ximo.spring.security.sdk.browser.handler.CustomAuthenticationFailureHandler;
import com.ximo.spring.security.sdk.browser.handler.CustomAuthenticationSuccessHandler;
import com.ximo.spring.security.sdk.core.config.properties.SdkSecurityProperties;
import com.ximo.spring.security.sdk.core.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author 朱文赵
 * @date 2018/6/24
 * @description
 */
@EnableWebSecurity
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    /** 安全配置 */
    @Autowired
    private SdkSecurityProperties sdkSecurityProperties;

    /** 失败处理控制器 */
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    /** 成功处理器 */
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //加到用户名密码过滤器的前面
                .addFilterBefore(new ValidateCodeFilter(customAuthenticationFailureHandler), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ACTUATOR")
                .antMatchers("/authentication/require", "/favicon.ico", "/code/image",
                        sdkSecurityProperties.getBrowser().getLoginPage()).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
