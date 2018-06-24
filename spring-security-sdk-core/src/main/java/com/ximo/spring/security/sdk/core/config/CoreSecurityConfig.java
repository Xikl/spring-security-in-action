package com.ximo.spring.security.sdk.core.config;

import com.ximo.spring.security.sdk.core.config.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 朱文赵
 * @date 2018/6/24
 * @description
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class CoreSecurityConfig {
}
