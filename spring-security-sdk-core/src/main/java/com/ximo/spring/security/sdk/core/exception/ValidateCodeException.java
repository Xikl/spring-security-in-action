package com.ximo.spring.security.sdk.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author 朱文赵
 * @date 2018/6/28
 * @description 自定义验证码异常
 */
public class ValidateCodeException extends AuthenticationException {


    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
