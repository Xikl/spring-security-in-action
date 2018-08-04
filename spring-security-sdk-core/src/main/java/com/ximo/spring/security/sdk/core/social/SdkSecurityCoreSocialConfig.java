package com.ximo.spring.security.sdk.core.social;

import com.ximo.spring.security.sdk.core.config.properties.SdkSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author 朱文赵
 * @date 2018/7/4
 * @description  配置类
 */
@Configuration
@EnableSocial
public class SdkSecurityCoreSocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SdkSecurityProperties sdkSecurityProperties;

    /** 用户自定义的实现 */
    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    /**
     * 获得连接工厂
     *
     * @param connectionFactoryLocator 获得连接查找器
     * @return 连接工厂 去数据库中查找用户 操作userConnection 表
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository =
                new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        if (connectionSignUp != null) {
            jdbcUsersConnectionRepository.setConnectionSignUp(connectionSignUp);
        }
        //设置前缀
        jdbcUsersConnectionRepository.setTablePrefix("sdk_");
        return jdbcUsersConnectionRepository;
    }

    @Bean
    public SpringSocialConfigurer sdkSocialSecurityConfigurer() {
        SdkSpringSocialConfigurer sdkSpringSocialConfigurer =
                new SdkSpringSocialConfigurer(sdkSecurityProperties.getSocial().getFilterProcessUrl());
        sdkSpringSocialConfigurer.signupUrl(sdkSecurityProperties.getBrowser().getRegisterPage());

        return sdkSpringSocialConfigurer;
    }

    /**
     * 将social的信息和用户自己的相互绑定
     *
     * @param connectionFactoryLocator
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
    }

}
