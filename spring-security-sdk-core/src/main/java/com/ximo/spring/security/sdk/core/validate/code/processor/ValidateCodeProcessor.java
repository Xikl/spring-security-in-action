package com.ximo.spring.security.sdk.core.validate.code.processor;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

/**
 * @author 朱文赵
 * @date 2018/7/1
 * @description 验证码处理器
 */
public interface ValidateCodeProcessor {

    /**
     * 创建校验码
     *
     * @param request request对象
     */
    void create(ServletWebRequest request) throws IOException, ServletRequestBindingException;


}
