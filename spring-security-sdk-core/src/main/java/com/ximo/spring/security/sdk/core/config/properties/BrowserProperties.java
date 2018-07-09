package com.ximo.spring.security.sdk.core.config.properties;

import com.ximo.spring.security.sdk.core.enums.LoginResponseTypeEnums;
import lombok.Data;

import static com.ximo.spring.security.sdk.core.constants.SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
import static com.ximo.spring.security.sdk.core.constants.SecurityConstants.DEFAULT_REGISTER_PAGE_URL;

/**
 * @author 朱文赵
 * @date 2018/6/24
 * @description
 */
@Data
public class BrowserProperties {

    /**
     * 用户自定义登录页 默认值为我们自定义的登录页
     */
    private String loginPage = DEFAULT_REGISTER_PAGE_URL;

    /** 用户自定义登录页 默认值为我们自定义的登录页 */
    private String registerPage = DEFAULT_LOGIN_PAGE_URL;

    /** 登录返回类型 */
    private LoginResponseTypeEnums loginType = LoginResponseTypeEnums.JSON;

    /** 过期时间为一天 */
    private int rememberMeSeconds = 60 * 60 * 24;

}
