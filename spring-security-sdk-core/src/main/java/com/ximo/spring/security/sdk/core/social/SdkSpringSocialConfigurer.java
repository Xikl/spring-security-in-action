package com.ximo.spring.security.sdk.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author 朱文赵
 * @date 2018/7/6
 * @description
 */
public class SdkSpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessUrl;

    public SdkSpringSocialConfigurer(String filterProcessUrl) {
        this.filterProcessUrl = filterProcessUrl;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter socialAuthenticationFilter = (SocialAuthenticationFilter)super.postProcess(object);
        socialAuthenticationFilter.setFilterProcessesUrl(filterProcessUrl);
        return (T) socialAuthenticationFilter;
    }
}
