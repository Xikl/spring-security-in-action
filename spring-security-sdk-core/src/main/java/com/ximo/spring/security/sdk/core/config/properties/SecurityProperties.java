package com.ximo.spring.security.sdk.core.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 朱文赵
 * @date 2018/6/24
 * @description 需要在#{@link EnableConfigurationProperties}中使用
 */
@Data
@ConfigurationProperties(prefix = "user-default.security")
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

}
