package com.ximo.spring.security.sdk.core.social.weixin.connect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ximo.spring.security.sdk.core.enums.ResultEnums;
import com.ximo.spring.security.sdk.core.exception.SpringSecuritySdkException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author 朱文赵
 * @date 2018/7/16
 * @description
 */
@Slf4j
public class WeixinOAuth2Template extends OAuth2Template {

    /** appId */
    private String clientId;

    /** appSecret*/
    private String clientSecret;

    /** accessToken */
    private String accessTokenUrl;

    /** 刷新token的url */
    private static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

    /** 构造函数注入这届参数 */
    public WeixinOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;
    }

    /**
     * 换区accessToken
     *
     * @param authorizationCode
     * @param redirectUri
     * @param additionalParameters
     * @return
     */
    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri,
                                         MultiValueMap<String, String> additionalParameters) {
        StringBuilder accessTokenRequestUrl = new StringBuilder(accessTokenUrl);
        accessTokenRequestUrl.append("?appId=").append(clientId);
        accessTokenRequestUrl.append("&secret=").append(clientSecret);
        accessTokenRequestUrl.append("&code=").append(authorizationCode);
        accessTokenRequestUrl.append("&grant_type=authorization_code");
        accessTokenRequestUrl.append("&redirect_uri=").append(redirectUri);

        return getAccessToken(accessTokenRequestUrl);
    }

    private AccessGrant getAccessToken(StringBuilder accessTokenRequestUrl) {
        log.info("获取accessToken的请求url：{}", accessTokenRequestUrl);
        String response = getRestTemplate().getForObject(accessTokenUrl, String.class);
        log.info("获取accessToken的响应结果为：{}", response);
        try {
            Map resultMap = new ObjectMapper().readValue(response, Map.class);
            //错误处理
            if(StringUtils.isNotBlank(MapUtils.getString(resultMap, "errcode"))){
                String errcode = MapUtils.getString(resultMap, "errcode");
                String errmsg = MapUtils.getString(resultMap, "errmsg");
                throw new SpringSecuritySdkException(ResultEnums.THIRD_PARTY_SERVICE_ERROR.getCode(), String.format("获取access token失败, errcode:%s, errmsg:%s", errcode, errmsg));
            }

            //拼装微信授权信息
            return new WeixinAccessGrant(
                    MapUtils.getString(resultMap, "access_token"),
                    MapUtils.getString(resultMap, "scope"),
                    MapUtils.getString(resultMap, "refresh_token"),
                    MapUtils.getLong(resultMap, "expires_in"),
                    MapUtils.getString(resultMap, "openId"));
        } catch (IOException e) {
            throw new SpringSecuritySdkException(ResultEnums.THIRD_PARTY_SERVICE_ERROR, e);
        }
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }


    /**
     * 构建获取授权码的请求。也就是引导用户跳转到微信的地址。
     */
    @Override
    public String buildAuthenticateUrl(OAuth2Parameters parameters) {
        String url = super.buildAuthenticateUrl(parameters);
        url = url + "&appid="+clientId+"&scope=snsapi_login";
        return url;
    }

    @Override
    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        return buildAuthenticateUrl(parameters);
    }
}
