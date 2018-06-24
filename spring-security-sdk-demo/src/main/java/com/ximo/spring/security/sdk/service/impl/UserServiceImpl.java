package com.ximo.spring.security.sdk.service.impl;

import com.ximo.spring.security.sdk.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author 朱文赵
 * @date 2018/6/23
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public boolean isUsernameExist(Integer userId, String username) {
        return false;
    }
}
