package com.ximo.spring.security.sdk.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * @author 朱文赵
 * @date 2018/7/4
 * @description qq服务实现类
 */
@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private static final String URL_GET_OPEN_ID = "https://graph.qq.com/oauth2.0/me?access_token=%s ";

    private static final String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;

    private String openId;

    @Autowired
    private ObjectMapper objectMapper;

    /** 将accessToken作为查询参数传输 */
    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String url = String.format(URL_GET_OPEN_ID, accessToken);
        String appIdResult = getRestTemplate().getForObject(url, String.class);
        log.info("appId{}", appIdResult);
        this.openId = StringUtils.substringBetween(appIdResult, "\"openId\":\"", "\"}");
    }


    @Override
    public QQUserInfo getQQUserInfo() {
        try {
            String url = String.format(URL_GET_USER_INFO, appId, openId);
            String result = getRestTemplate().getForObject(url, String.class);
            log.info("result{}", result);
            QQUserInfo qqUserInfo = objectMapper.readValue(result, QQUserInfo.class);
            qqUserInfo.setOpenId(openId);
            return qqUserInfo;
        } catch (IOException e) {
            throw new RuntimeException("获取用户信息失败", e);
        }
    }
}