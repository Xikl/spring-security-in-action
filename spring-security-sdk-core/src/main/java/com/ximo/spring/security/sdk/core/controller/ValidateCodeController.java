package com.ximo.spring.security.sdk.core.controller;

import com.ximo.spring.security.sdk.core.util.ServletUtils;
import com.ximo.spring.security.sdk.core.validate.code.processor.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.Map;

/**
 * @author 朱文赵
 * @date 2018/6/25
 * @description 验证码控制器
 */
@RestController
public class ValidateCodeController {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessorMap;


    /**
     * 验证码逻辑
     *
     * @param type 验证类型
     */
    @GetMapping("/code/{type}")
    public void generateImageCode(@PathVariable("type") String type) throws IOException, ServletRequestBindingException {
        ServletWebRequest servletWebRequest = ServletUtils.getServletWebRequest();
        String validateCodeProcessorName = getValidateCodeProcessorName(type);
        validateCodeProcessorMap.get(validateCodeProcessorName).create(servletWebRequest);
    }

    /**
     * 获得验证码处理器的名字
     *
     * @param type 验证类型
     * @return 验证码处理器的名字
     */
    private String getValidateCodeProcessorName(String type) {
        return String.format("%s%s", type, ValidateCodeProcessor.class.getSimpleName());
    }

}
