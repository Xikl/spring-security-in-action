package com.ximo.spring.security.sdk.core.config.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 朱文赵
 * @date 2018/6/30
 * @description 验证码配置选项
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImageCodeProperties extends SmsCodeProperties{

    /** 验证码宽度 */
    private int width = 67;

    /** 验证码高度 */
    private int height = 23;

    public ImageCodeProperties() {
        setLength(4);
    }
}
