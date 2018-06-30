package com.ximo.spring.security.sdk.browser.config;

import com.ximo.spring.security.sdk.browser.handler.CustomAuthenticationFailureHandler;
import com.ximo.spring.security.sdk.browser.handler.CustomAuthenticationSuccessHandler;
import com.ximo.spring.security.sdk.core.config.properties.SdkSecurityProperties;
import com.ximo.spring.security.sdk.core.validate.code.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author 朱文赵
 * @date 2018/6/24
 * @description
 */
@EnableWebSecurity
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter(customAuthenticationFailureHandler, sdkSecurityProperties);
        //调用配置选项
        validateCodeFilter.afterPropertiesSet();
        //security 配置
        http
                //加到用户名密码过滤器的前面
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
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
                .rememberMe()
                .tokenValiditySeconds(sdkSecurityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .tokenRepository(persistentTokenRepository())
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
