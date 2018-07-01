package com.ximo.spring.security.sdk.core.validate.code.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;

/**
 * @author 朱文赵
 * @date 2018/6/25
 * @description 图形验证码信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageCode extends ValidateCode {

    /** 验证码图片 */
    private BufferedImage image;

    /** 第三个参数为过期时间点 */
    public ImageCode(BufferedImage image, String code, long expireIn) {
        super(code, expireIn);
        this.image = image;
    }



}
