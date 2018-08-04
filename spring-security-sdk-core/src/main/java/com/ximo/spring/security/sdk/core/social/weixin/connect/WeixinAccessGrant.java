package com.ximo.spring.security.sdk.core.social.weixin.connect;

import lombok.Getter;
import lombok.Setter;
import org.springframework.social.oauth2.AccessGrant;

/**
 * @author 朱文赵
 * @date 2018/7/16
 * @description
 * 微信的access_token信息。与标准OAuth2协议不同，微信在获取access_token时会同时返回openId,并没有单独的通过accessToke换取openId的服务
 * 所以在这里继承了标准AccessGrant，添加了openId字段，作为对微信access_token信息的封装。
 */
@Getter
@Setter
public class WeixinAccessGrant extends AccessGrant {

    private String openId;

    public WeixinAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, String openId) {
        super(accessToken, scope, refreshToken, expiresIn);
        this.openId = openId;
    }

}
