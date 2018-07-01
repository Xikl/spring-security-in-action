package com.ximo.spring.security.sdk.core.constants;

/**
 * @author 朱文赵
 * @date 2018/6/28
 * @description
 */
public interface SecurityConstants {

    /** 验证码放入session中的前缀 */
    String SESSION_KEY_FOR_CODE_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";

}
