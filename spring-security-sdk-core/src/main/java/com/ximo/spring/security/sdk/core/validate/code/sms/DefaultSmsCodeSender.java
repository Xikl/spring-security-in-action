package com.ximo.spring.security.sdk.core.validate.code.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 朱文赵
 * @date 2018/6/30
 * @description 发送短信默认实现
 */
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {

    /**
     * 默认发送短信实现
     *
     * @param mobile 手机
     * @param code 发送的短信验证码
     */
    @Override
    public void send(String mobile, String code) {
        log.info("向手机{}发送短信验证码{}", mobile, code);
    }
}
