package com.ximo.spring.security.sdk.browser.config;

import com.ximo.spring.security.sdk.browser.handler.CustomAuthenticationFailureHandler;
import com.ximo.spring.security.sdk.browser.handler.CustomAuthenticationSuccessHandler;
import com.ximo.spring.security.sdk.core.authentication.AbstractChannelSecurityConfig;
import com.ximo.spring.security.sdk.core.authentication.mobile.SmsValidateCodeAuthenticationSecurityConfig;
import com.ximo.spring.security.sdk.core.config.properties.SdkSecurityProperties;
import com.ximo.spring.security.sdk.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

import static com.ximo.spring.security.sdk.core.constants.SecurityConstants.*;

/**
 * @author 朱文赵
 * @date 2018/6/24
 * @description
 */
@EnableWebSecurity
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private DataSource dataSource;

    /** 安全配置 */
    @Autowired
    private SdkSecurityProperties sdkSecurityProperties;

    /** 用户信息service */
    @Qualifier("userServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    /** 失败处理控制器 */
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    /** 成功处理器 */
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private SmsValidateCodeAuthenticationSecurityConfig smsValidateCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //加入父类的密码校验
        applyPasswordAuthenticationConfig(http);

        //security 配置
        http
                //将配置信息放到加到配置链路中
                    .apply(validateCodeSecurityConfig)
                .and()
                    .apply(smsValidateCodeAuthenticationSecurityConfig)
                .and()
                .rememberMe()
                    .tokenValiditySeconds(sdkSecurityProperties.getBrowser().getRememberMeSeconds())
                    .userDetailsService(userDetailsService)
                    .tokenRepository(persistentTokenRepository())
                .and()
                    .authorizeRequests()
                    .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ACTUATOR")
                    .antMatchers(DEFAULT_UN_AUTHENTICATION_URL,
                            DEFAULT_LOGIN_PROCESSING_URL_FORM,
                            "/favicon.ico",
                            DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                            sdkSecurityProperties.getBrowser().getLoginPage()).permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                .csrf().disable();



    }

    /** 密码解析器 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** 创建rememberMe的存储仓库 默认为内存 */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
}
