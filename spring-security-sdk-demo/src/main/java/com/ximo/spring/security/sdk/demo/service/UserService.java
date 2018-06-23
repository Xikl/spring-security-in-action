package com.ximo.spring.security.sdk.demo.service;

/**
 * @author 朱文赵
 * @date 2018/6/23
 * @description
 */
public interface UserService {

    /**
     * 判断用户名是否重复
     *
     * @param userId   用户ID
     * @param username 用户名
     * @return 是否存在
     */
    boolean isUsernameExist(Integer userId, String username);


}
