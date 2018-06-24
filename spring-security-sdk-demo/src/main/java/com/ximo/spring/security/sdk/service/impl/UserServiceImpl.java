package com.ximo.spring.security.sdk.service.impl;

import com.ximo.spring.security.sdk.domain.User;
import com.ximo.spring.security.sdk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 朱文赵
 * @date 2018/6/23
 * @description
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean isUsernameExist(Integer userId, String username) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username:", username);
        //1.从数据库根据用户名中查找用户信息
        //2.根据查找到用户的信息判断用户是否已经被冻结
        //passwordEncoder.encode 操作应该是在用户注册的时候 做的操作 这里应该只拿到数据库中存储的密码
        return new User(1, username, passwordEncoder.encode("123456"), new Date());
    }
}
