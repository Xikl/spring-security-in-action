package com.ximo.spring.security.sdk.core.config;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 朱文赵
 * @date 2018/6/28
 * @description
 */
@Configuration
public class BeanConfig {

    /** 配置objectMapper */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


}
