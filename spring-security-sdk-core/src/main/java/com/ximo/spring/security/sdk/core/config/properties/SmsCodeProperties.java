package com.ximo.spring.security.sdk.core.config.properties;

import lombok.Data;

/**
 * @author 朱文赵
 * @date 2018/6/30
 * @description 验证码配置选项
 */
@Data
public class SmsCodeProperties {

    /** 短信验证码长度 */
    private int length = 6;

    /** 短信验证码过期时间 */
    private int expireIn = 60;

    /** 短信验证码需要拦截的url */
    private String interceptUrl;

}
