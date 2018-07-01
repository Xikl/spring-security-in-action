package com.ximo.spring.security.sdk.core.validate.code.generator;

import com.ximo.spring.security.sdk.core.config.properties.SdkSecurityProperties;
import com.ximo.spring.security.sdk.core.config.properties.SmsCodeProperties;
import com.ximo.spring.security.sdk.core.validate.code.entity.ValidateCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author 朱文赵
 * @date 2018/6/30
 * @description 图形验证码
 */
@Component
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SdkSecurityProperties sdkSecurityProperties;

    /**
     * 创建短信验证码
     *
     * @param request {@link ServletWebRequest}请求对象
     * @return 短信验证码
     */
    @Override
    public ValidateCode generate(ServletWebRequest request) {
        SmsCodeProperties sms = sdkSecurityProperties.getCode().getSms();
        String code = RandomStringUtils.randomNumeric(sms.getLength());
        return new ValidateCode(code, sms.getExpireIn());
    }

}
