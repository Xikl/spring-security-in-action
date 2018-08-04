package com.ximo.spring.security.sdk.core.social.weixin.config;

import com.ximo.spring.security.sdk.core.config.properties.SdkSecurityProperties;
import com.ximo.spring.security.sdk.core.config.properties.WeiXinProperties;
import com.ximo.spring.security.sdk.core.social.weixin.connect.WeixinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.security.AuthenticationNameUserIdSource;

/**
 * @author 朱文赵
 * @date 2018/7/16
 * @description
 */
@Configuration
@ConditionalOnProperty(prefix = "user-default.security.social.weixin", name = "app-id")
public class WeixinAutoConfig extends SocialConfigurerAdapter {


    @Autowired
    private SdkSecurityProperties sdkSecurityProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        //拿到微信的appId和AppSecret
        WeiXinProperties weiXinProperties = sdkSecurityProperties.getSocial().getWinxin();
        //构建一个连接工厂
        WeixinConnectionFactory weixinConnectionFactory =
                new WeixinConnectionFactory(weiXinProperties.getProviderId(), weiXinProperties.getAppId(), weiXinProperties.getAppSecret());
        connectionFactoryConfigurer.addConnectionFactory(weixinConnectionFactory);
    }

    /**
     * The getUserId() method simply returns a String that uniquely identifies the current user
     *
     * @return
     */
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }
}
