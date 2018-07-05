package com.ximo.spring.security.sdk.core.social.qq.connect;

import com.ximo.spring.security.sdk.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author 朱文赵
 * @date 2018/7/4
 * @description 连接工厂
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {


    /**
     * Create a {@link OAuth2ConnectionFactory}.
     *
     * @param providerId the provider id e.g. "facebook"
     * @param appId      appId
     * @param appSecret  appSecret
     */
    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }
}
