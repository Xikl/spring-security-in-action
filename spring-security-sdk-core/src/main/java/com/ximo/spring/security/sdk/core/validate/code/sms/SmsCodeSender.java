package com.ximo.spring.security.sdk.core.validate.code.sms;

/**
 * @author 朱文赵
 * @date 2018/6/30
 * @description
 */
public interface SmsCodeSender {

    /**
     * 发送短信
     *
     * @param mobile 手机
     * @param code 发送的短信验证码
     */
    void send(String mobile, String code);


}
