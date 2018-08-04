package com.ximo.spring.security.sdk.core.social.weixin.connect;

import com.ximo.spring.security.sdk.core.social.weixin.api.Weixin;
import com.ximo.spring.security.sdk.core.social.weixin.api.WeixinUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author 朱文赵
 * @date 2018/7/16
 * @description
 */
public class WeixinAdapter implements ApiAdapter<Weixin> {

    private String openId;

    public WeixinAdapter(String openId) {
        this.openId = openId;
    }

    @Override
    public boolean test(Weixin api) {
        return true;
    }

    @Override
    public void setConnectionValues(Weixin api, ConnectionValues values) {
        WeixinUserInfo weixinUserInfo = api.getWeixinUserInfo(openId);
        //openId
        values.setProviderUserId(weixinUserInfo.getOpenid());
        //头像
        values.setImageUrl(weixinUserInfo.getHeadimgurl());
        //昵称
        values.setDisplayName(weixinUserInfo.getNickname());
        //个人主页 qq没有设为null
        values.setProfileUrl(null);
    }

    @Override
    public UserProfile fetchUserProfile(Weixin api) {
        return null;
    }

    @Override
    public void updateStatus(Weixin api, String message) {
        //do nothing
    }
}
