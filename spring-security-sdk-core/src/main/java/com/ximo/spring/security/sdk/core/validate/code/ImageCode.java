package com.ximo.spring.security.sdk.core.validate.code;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author 朱文赵
 * @date 2018/6/25
 * @description 验证码信息
 */
@Data
@AllArgsConstructor
public class ImageCode {

    /** 验证码图片 */
    private BufferedImage image;

    /** 验证码 */
    private String code;

    /** 过期时间 */
    private LocalDateTime expiredTime;

    /** 第三个参数为过期时间点 */
    public ImageCode(BufferedImage image, String code, long expireIn) {
        this.image = image;
        this.code = code;
        this.expiredTime = LocalDateTime.now().plusSeconds(expireIn);
    }
}
