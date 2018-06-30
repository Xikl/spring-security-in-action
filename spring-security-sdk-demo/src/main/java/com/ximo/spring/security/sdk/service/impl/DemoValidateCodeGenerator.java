package com.ximo.spring.security.sdk.service.impl;

import com.ximo.spring.security.sdk.core.validate.code.entity.ImageCode;
import com.ximo.spring.security.sdk.core.validate.code.generator.ValidateCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author 朱文赵
 * @date 2018/6/30
 * @description
 */
@Slf4j
//@Component("imageCodeGenerator")
public class DemoValidateCodeGenerator implements ValidateCodeGenerator {

    @Override
    public ImageCode generate(ServletWebRequest request) {
        log.info("更加高级的图形验证码生成");
        return null;
    }
}
