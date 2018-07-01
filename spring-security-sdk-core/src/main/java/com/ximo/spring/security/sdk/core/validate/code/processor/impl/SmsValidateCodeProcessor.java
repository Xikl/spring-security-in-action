package com.ximo.spring.security.sdk.core.validate.code.processor.impl;

import com.ximo.spring.security.sdk.core.validate.code.entity.ValidateCode;
import com.ximo.spring.security.sdk.core.validate.code.sender.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import static com.ximo.spring.security.sdk.core.constants.SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;

/**
 * @author 朱文赵
 * @date 2018/7/1
 * @description
 */
@Component
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws ServletRequestBindingException {
        //获得手机号
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), DEFAULT_PARAMETER_NAME_MOBILE);
        //发送短信验证码
        smsCodeSender.send(mobile, validateCode.getCode());
    }
}
