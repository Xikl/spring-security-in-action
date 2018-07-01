package com.ximo.spring.security.sdk.core.config;

import com.ximo.spring.security.sdk.core.config.properties.SdkSecurityProperties;
import com.ximo.spring.security.sdk.core.validate.code.generator.impl.ImageValidateCodeGenerator;
import com.ximo.spring.security.sdk.core.validate.code.generator.ValidateCodeGenerator;
import com.ximo.spring.security.sdk.core.validate.code.sender.impl.DefaultSmsCodeSender;
import com.ximo.spring.security.sdk.core.validate.code.sender.SmsCodeSender;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 朱文赵
 * @date 2018/6/28
 * @description bean生成配置类
 */
@Configuration
public class BeanConfig {

    @Autowired
    private SdkSecurityProperties sdkSecurityProperties;

    /** 配置objectMapper */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    /**
     * 配置验证码生成器
     * 其中添加了{@link ConditionalOnMissingBean}意味着当系统中存在一个imageCodeGenerator的时候 则不会注入该bean
     *
     * @return 验证码生成器
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        return new ImageValidateCodeGenerator(sdkSecurityProperties);
    }

    /**
     * 构建一个默认的发送短信类
     * 其中添加了{@link ConditionalOnMissingBean}意味着当系统中存在一个imageCodeGenerator的时候 则不会注入该bean
     * 这里和上面的方法采用不一样的是 这里是采用class的写法，当找到这个类的实现 就不在注册该bean
     *
     * @return 发送短信类
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }
}
