package com.ximo.spring.security.sdk.core.social.qq.config;

import com.ximo.spring.security.sdk.core.config.properties.QQProperties;
import com.ximo.spring.security.sdk.core.config.properties.SdkSecurityProperties;
import com.ximo.spring.security.sdk.core.social.qq.connect.QQConnectionFactory;
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
 * @date 2018/7/4
 * @description 只有在配置中国 appId有值才生效
 */
@Configuration
@ConditionalOnProperty(prefix = "user-default.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialConfigurerAdapter {

    @Autowired
    private SdkSecurityProperties sdkSecurityProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        QQProperties qqProperties = sdkSecurityProperties.getSocial().getQq();
        QQConnectionFactory qqConnectionFactory =
                new QQConnectionFactory(qqProperties.getProviderId(), qqProperties.getAppId(), qqProperties.getAppSecret());
        connectionFactoryConfigurer.addConnectionFactory(qqConnectionFactory);
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
