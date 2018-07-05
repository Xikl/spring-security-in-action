package com.ximo.spring.security.sdk.core.social.qq.connect;

import com.ximo.spring.security.sdk.core.social.qq.api.QQ;
import com.ximo.spring.security.sdk.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * @author 朱文赵
 * @date 2018/7/4
 * @description
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;

    /** 让用户同意授权的url */
    private static final String AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize";

    /** 携带授权码请求access_token的url */
    private static final String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";

    /**
     * Create a new {@link OAuth2ServiceProvider}.
     *
     */
    public QQServiceProvider(String appId, String appSecret) {
        super(new OAuth2Template(appId, appSecret, AUTHORIZE_URL, ACCESS_TOKEN_URL));
    }


    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }
}
