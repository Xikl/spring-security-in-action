package com.ximo.spring.security.sdk.core.validate.code.processor.impl;

import com.ximo.spring.security.sdk.core.enums.ValidateCodeTypeEnums;
import com.ximo.spring.security.sdk.core.exception.ValidateCodeException;
import com.ximo.spring.security.sdk.core.validate.code.entity.ValidateCode;
import com.ximo.spring.security.sdk.core.validate.code.generator.ValidateCodeGenerator;
import com.ximo.spring.security.sdk.core.validate.code.processor.ValidateCodeProcessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.ximo.spring.security.sdk.core.constants.SecurityConstants.SESSION_KEY_FOR_CODE_PREFIX;

/**
 * @author 朱文赵
 * @date 2018/7/1
 * @description
 */
public abstract class AbstractValidateCodeProcessor<T extends ValidateCode> implements ValidateCodeProcessor {

    /** 操作session的工具类 */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /** 依赖查找 会找到所有{@link ValidateCodeGenerator}的实现 然后存为一个map*/
    @Autowired
    private Map<String, ValidateCodeGenerator<T>> validateCodeGeneratorMap;

    /**
     * 创建校验码
     * 然后保存到session 发送
     *
     * @param request request对象
     * @throws IOException io异常
     * @throws ServletRequestBindingException 参数保定异常
     */
    @Override
    public void create(ServletWebRequest request) throws IOException, ServletRequestBindingException {
        T validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    /**
     * 保存验证码到session中
     *
     * @param request request
     * @param validateCode 验证码
     */
    private void save(ServletWebRequest request, T validateCode) {
        sessionStrategy.setAttribute(request, getSessionKey(request), validateCode);
    }

    /**
     * 获得验证码
     *
     * @param request ServletWebRequest
     * @return 验证码
     */
    private T generate(ServletWebRequest request) {
        String validateCodeType = getValidateCodeType(request);
        String generatorName = getGeneratorName(validateCodeType);
        Optional<ValidateCodeGenerator<T>> validateCodeGeneratorOptional =
                Optional.ofNullable(validateCodeGeneratorMap.get(generatorName));
        
        return validateCodeGeneratorOptional
                .orElseThrow(() -> new ValidateCodeException("验证码生成器" + generatorName + "不存在"))
                .generate(request);
    }

    private String getValidateCodeType(ServletWebRequest request) {
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
    }

    /**
     * 获得存入session中的key
     *
     * @param request request工具类
     * @return 存入session中的key
     */
    private String getSessionKey(ServletWebRequest request) {
        return String.format("%s%s", SESSION_KEY_FOR_CODE_PREFIX, getValidateCodeType(request).toUpperCase());
    }

    /**
     * 获得生成器的名字
     *
     * @param validateCodeType 类型
     * @return 生成器的名字
     */
    private String getGeneratorName(String validateCodeType) {
        return String.format("%s%s", validateCodeType, ValidateCodeGenerator.class.getSimpleName());
    }

    /**
     * 发送验证码，由子类实现
     *
     * @param request request对象
     * @param validateCode 需要发送的验证码
     * @throws IOException IOException
     * @throws ServletRequestBindingException ServletRequestBindingException
     */
    protected abstract void send(ServletWebRequest request, T validateCode) throws IOException, ServletRequestBindingException;

}
