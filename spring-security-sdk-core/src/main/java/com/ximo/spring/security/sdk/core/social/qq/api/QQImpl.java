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

    /** 获取openId的url 需要传入accessToken的信息 */
    private static final String URL_GET_OPEN_ID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    /** 拿accessToken 和 openId 和appId换取用户信息 */
    private static final String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    /** appId */
    private String appId;

    /** openId */
    private String openId;

    /** 还有一个参数accessToken 已经存在于父类 */

    @Autowired
    private ObjectMapper objectMapper;

    /** 将accessToken作为查询参数传输 */
    public QQImpl(String accessToken, String appId) {
        //将accessToken作为查询参数传输
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String url = String.format(URL_GET_OPEN_ID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);
        log.info("openIdResult{}", result);
        //获得OpenId
        this.openId = StringUtils.substringBetween(result, "\"openId\":\"", "\"}");
    }


    /**
     * 获取用户信息
     *
     * @return qq 用户信息
     */
    @Override
    public QQUserInfo getQQUserInfo() {
        try {
            String url = String.format(URL_GET_USER_INFO, appId, openId);
            String result = getRestTemplate().getForObject(url, String.class);
            log.info("qqUserInfo{}", result);
            QQUserInfo qqUserInfo = objectMapper.readValue(result, QQUserInfo.class);
            qqUserInfo.setOpenId(openId);
            return qqUserInfo;
        } catch (IOException e) {
            throw new RuntimeException("获取用户信息失败", e);
        }
    }
}
