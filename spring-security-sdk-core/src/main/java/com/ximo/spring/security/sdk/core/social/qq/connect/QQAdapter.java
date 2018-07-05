package com.ximo.spring.security.sdk.core.social.qq.connect;

import com.ximo.spring.security.sdk.core.social.qq.api.QQ;
import com.ximo.spring.security.sdk.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import java.io.IOException;

/**
 * @author 朱文赵
 * @date 2018/7/4
 * @description
 */
public class QQAdapter implements ApiAdapter<QQ> {


    /**
     * 测试qq服务是否可用
     *
     * @param api qq service
     * @return 一直设为成功
     */
    @Override
    public boolean test(QQ api) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo qqUserInfo = api.getQQUserInfo();
        values.setDisplayName(qqUserInfo.getNickname());
        values.setImageUrl(qqUserInfo.getFigureurl_qq_1());
        //个人主页 qq没有设为null
        values.setProfileUrl(null);
        //qq服务商的userId 用户在供应商中的唯一标示
        values.setProviderUserId(qqUserInfo.getOpenId());

    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {
        //do nothing
    }
}
