package com.ximo.spring.security.sdk.service.impl;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * @author 朱文赵
 * @date 2018/7/9
 * @description
 */
@Component
public class DemoConnectionRegister implements ConnectionSignUp {


    @Override
    public String execute(Connection<?> connection) {
        //根据社交用户的信息默认创建用户
        //返回用户唯一标示
        return null;
    }
}
