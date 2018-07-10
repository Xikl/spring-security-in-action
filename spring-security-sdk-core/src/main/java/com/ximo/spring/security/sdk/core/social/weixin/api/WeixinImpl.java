package com.ximo.spring.security.sdk.core.social.weixin.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ximo.spring.security.sdk.core.enums.ResultEnums;
import com.ximo.spring.security.sdk.core.exception.SpringSecuritySdkException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author 朱文赵
 * @date 2018/7/10
 * @description
 */
@Slf4j
public class WeixinImpl extends AbstractOAuth2ApiBinding implements Weixin  {


    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String ERR_CODE = "errcode";


    /**
     * 获取用户信息的url
     */
    private static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=%s";

    /** 将参数放到url中 */
    public WeixinImpl(String accessToekn) {
        super(accessToekn, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    /** 重写信息转化方法 避免乱码 */
    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        //移除父类中的默认方法 具体请看super
        messageConverters.remove(0);
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return messageConverters;
    }

    @Override
    public WeixinUserInfo getWeixinUserInfo(String openId) {
        try {
            String url = String.format(URL_GET_USER_INFO, openId);
            String result = getRestTemplate().getForObject(url, String.class);
            if (StringUtils.contains(result, ERR_CODE)) {
                log.error("【微信】获得微信信息的时候回调失败, json为{}", result);
                throw new SpringSecuritySdkException(ResultEnums.THIRD_PARTY_SERVICE_ERROR);
            }
            return objectMapper.readValue(result, WeixinUserInfo.class);
        } catch (IOException e) {
            log.info("【微信】json转化为对象WeixinUserInfo的时候报错");
            throw new SpringSecuritySdkException(ResultEnums.THIRD_PARTY_SERVICE_ERROR, e);
        }
    }
}
