package com.ximo.spring.security.sdk.core.config.properties;

import lombok.Data;

/**
 * @author 朱文赵
 * @date 2018/6/30
 * @description 验证码配置
 */
@Data
public class ValidateCodeProperties {

    /** 将图形验证码包装放到这一层 */
    private ImageCodeProperties image = new ImageCodeProperties();


}
