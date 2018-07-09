package com.ximo.spring.security.sdk.browser.support;

import lombok.Data;

/**
 * @author 朱文赵
 * @date 2018/7/9
 * @description social用户信息
 */
@Data
public class SocialUserInfo {

    /** 提供者 */
    private String providerId;

    /** 提供的用户Id如 openId */
    private String providerUserId;

    /** 昵称*/
    private String nickname;

    /** 头像*/
    private String headerImg;



}
