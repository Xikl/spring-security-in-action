package com.ximo.spring.security.sdk.core.social.qq.connect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * @author 朱文赵
 * @date 2018/7/7
 * @description
 */
@Slf4j
public class QQOAuth2Template extends OAuth2Template {

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        //只有这个属性为true的时候 才会去进行拼装
        setUseParametersForClientAuthentication(true);
    }

    /**
     * 由于qq返回的结果是一个字符串，不是一个json 所以我们需要覆盖该方法
     *
     * @param accessTokenUrl
     * @param parameters
     * @return
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String responseUrl = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        log.info("【QQ获得accessToken返回值】responseUrl：{} ", responseUrl);
        //截取字符串
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseUrl, "&");
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = Long.valueOf(StringUtils.substringAfterLast(items[1], "="));
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");
        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }
}
