package com.ximo.spring.security.sdk.core.config.properties;

import lombok.Data;

/**
 * @author 朱文赵
 * @date 2018/6/30
 * @description 验证码配置选项
 */
@Data
public class ImageCodeProperties {

    /** 验证码宽度 */
    private int width = 67;
    /** 验证码高度 */
    private int height = 23;
    /** 验证码长度 */
    private int length = 4;
    /** 验证码过期时间 */
    private int exprieIn = 60;





}
