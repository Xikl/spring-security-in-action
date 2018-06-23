package com.ximo.spring.security.sdk.demo.config;

import com.ximo.spring.security.sdk.demo.interceptor.SdkInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 朱文赵
 * @date 2018/6/23
 * @description
 */
@Configuration
public class WebInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private SdkInterceptor sdkInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sdkInterceptor);
    }
}
