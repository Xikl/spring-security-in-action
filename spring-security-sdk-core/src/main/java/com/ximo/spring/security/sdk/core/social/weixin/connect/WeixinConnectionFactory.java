package com.ximo.spring.security.sdk.core.social.weixin.connect;

import com.ximo.spring.security.sdk.core.social.weixin.api.Weixin;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * @author 朱文赵
 * @date 2018/7/16
 * @description
 */
public class WeixinConnectionFactory extends OAuth2ConnectionFactory<Weixin> {


    /**
     * 构建连接工厂
     *
     * @param providerId 类似 qq 或者 weixin
     * @param appId appId
     * @param appSecret appSecret
     */
    public WeixinConnectionFactory(String providerId, String appId, String appSecret) {
        //新建一个微服务提供者 和 微信适配器
        super(providerId, new WeixinServiceProvider(appId, appSecret), new WeixinAdapter(appId));
    }

    /**
     * 获得openId
     *
     * @param accessGrant 授权信息
     * @return openId
     */
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if (accessGrant instanceof WeixinAccessGrant) {
            return ((WeixinAccessGrant) accessGrant).getOpenId();
        }
        return null;
    }

    @Override
    public Connection<Weixin> createConnection(ConnectionData data) {
        return new OAuth2Connection<>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
    }

    @Override
    public Connection<Weixin> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(),
                getApiAdapter(extractProviderUserId(accessGrant)));
    }

    /** 存入用户openId */
    private ApiAdapter<Weixin> getApiAdapter(String providerUserId) {
        return new WeixinAdapter(providerUserId);
    }

    /** 私有是不可能私有的 */
    private OAuth2ServiceProvider<Weixin> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<Weixin>) getServiceProvider();
    }
}
