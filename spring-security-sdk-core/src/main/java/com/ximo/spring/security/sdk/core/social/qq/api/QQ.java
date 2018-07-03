package com.ximo.spring.security.sdk.core.social.qq.api;

import java.io.IOException;

/**
 * @author 朱文赵
 * @date 2018/7/4
 * @description
 */
public interface QQ {


    /**
     * 获得用户信息
     *
     * @return qq用户信息
     */
    QQUserInfo getQQUserInfo() throws IOException;


}
