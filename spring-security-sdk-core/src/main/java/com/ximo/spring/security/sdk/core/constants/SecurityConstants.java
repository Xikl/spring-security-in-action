package com.ximo.spring.security.sdk.core.constants;

/**
 * @author 朱文赵
 * @date 2018/6/28
 * @description
 */
public interface SecurityConstants {

    /** post请求方法 */
    String POST_METHOD = "POST";

    /** get方法 */
    String GET_METHOD = "get";


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

    /**
     * 当请求需要身份认证时，默认跳转的url
     *
     */
    String DEFAULT_UN_AUTHENTICATION_URL = "/authentication/require";

    /**  默认的用户名密码登录请求处理url */
    String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/form";

    /** 默认的手机验证码登录请求处理url */
    String DEFAULT_LOGIN_PROCESSING_URL_MOBILE = "/authentication/mobile";

    /**
     * 默认登录页面
     */
    String DEFAULT_LOGIN_PAGE_URL = "/security-sdk-login.html";


    String DEFAULT_REGISTER_PAGE_URL = "/security-sdk-register.html";
    /**
     * session失效默认的跳转地址
     */
    String DEFAULT_SESSION_INVALID_URL = "/session/invalid";

    /**
     * 默认的处理验证码的url前缀
     */
   String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";
}
