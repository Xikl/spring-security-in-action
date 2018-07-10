package com.ximo.spring.security.sdk.core.config.properties;

import lombok.Data;

/**
 * @author 朱文赵
 * @date 2018/7/10
 * @description
 */
@Data
public class WeiXinProperties {

    private String appId;

    private String appSecret;

    /**
     * 第三方id，用来决定发起第三方登录的url，默认是 weixin。
     */
    private String providerId = "weixin";


}
