package com.ximo.spring.security.sdk.core.social.weixin.api;

/**
 * @author 朱文赵
 * @date 2018/7/10
 * @description
 */
public interface Weixin {

    /**
     * 获得用户信息
     *
     * @param openId openId 和qq不一样的地方就是它拿到accessToken的同时拿到了openId
     * @return 微信用户信息
     */
    WeixinUserInfo getWeixinUserInfo(String openId);

}
