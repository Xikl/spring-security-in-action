package com.ximo.spring.security.sdk.core.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author 朱文赵
 * @date 2018/7/4
 * @description
 */
@Configuration
@EnableSocial
public class SdkSecurityCoreSocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

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
        //设置前缀
        jdbcUsersConnectionRepository.setTablePrefix("sdk_");
        return jdbcUsersConnectionRepository;
    }

    @Bean
    public SpringSocialConfigurer sdkSocialSecurityConfigurer() {
        return new SpringSocialConfigurer();
    }

}
