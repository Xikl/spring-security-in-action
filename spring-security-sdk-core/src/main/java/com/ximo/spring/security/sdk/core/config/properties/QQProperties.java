package com.ximo.spring.security.sdk.core.config.properties;

import lombok.Data;

/**
 * @author 朱文赵
 * @date 2018/7/4
 * @description
 */
@Data
public class QQProperties{

    private String appId;

    private String appSecret;

    private String providerId = "qq";

}
