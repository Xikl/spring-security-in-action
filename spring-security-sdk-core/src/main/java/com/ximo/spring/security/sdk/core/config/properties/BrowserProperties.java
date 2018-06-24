package com.ximo.spring.security.sdk.core.config.properties;

import lombok.Data;

/**
 * @author 朱文赵
 * @date 2018/6/24
 * @description
 */
@Data
public class BrowserProperties {

    /** 用户自定义登录页 默认值为我们自定义的登录页 */
    private String loginPage = "/security-sdk-login.html";


}
