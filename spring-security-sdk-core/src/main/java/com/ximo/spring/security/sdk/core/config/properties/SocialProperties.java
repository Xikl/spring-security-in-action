package com.ximo.spring.security.sdk.core.config.properties;

import lombok.Data;

/**
 * @author 朱文赵
 * @date 2018/7/4
 * @description
 */
@Data
public class SocialProperties {

    /** qq相关配置 */
    private QQProperties qq = new QQProperties();

    /** 过滤器的处理的url */
    private String filterProcessUrl = "/auth";


}
