package com.ximo.spring.security.sdk.demo.config;

import com.ximo.spring.security.sdk.demo.filter.SdkFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 朱文赵
 * @date 2018/6/23
 * @description
 */
@Configuration
public class WebFilterConfig {

    /** 注册sdkFilter 效果和加Component一致 */
    @Bean
    public FilterRegistrationBean sdkFilter() {
        //声明注册filter的注册类
        FilterRegistrationBean<SdkFilter> registrationBean = new FilterRegistrationBean<>();
        List<String> urls = new ArrayList<>();
        urls.add("/user/**");
        registrationBean.setFilter(new SdkFilter());
        registrationBean.setUrlPatterns(urls);
        return registrationBean;
    }


}
