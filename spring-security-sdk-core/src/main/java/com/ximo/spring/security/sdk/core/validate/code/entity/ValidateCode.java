package com.ximo.spring.security.sdk.core.validate.code.entity;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author 朱文赵
 * @date 2018/6/25
 * @description 短信验证码
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ValidateCode {

    /** 验证码 */
    private String code;

    /** 过期时间 */
    private LocalDateTime expiredTime;

    /** 第三个参数为过期时间点 */
    public ValidateCode(String code, long expireIn) {
        this.code = code;
        this.expiredTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    /** 判断该验证码是否过期 */
    public boolean isExpire() {
        return LocalDateTime.now().isAfter(expiredTime);
    }
}
