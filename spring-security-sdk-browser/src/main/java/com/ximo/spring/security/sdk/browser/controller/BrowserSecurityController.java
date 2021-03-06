package com.ximo.spring.security.sdk.browser.controller;

import com.ximo.spring.security.sdk.browser.support.SocialUserInfo;
import com.ximo.spring.security.sdk.core.config.properties.SdkSecurityProperties;
import com.ximo.spring.security.sdk.core.enums.ResultEnums;
import com.ximo.spring.security.sdk.core.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.ximo.spring.security.sdk.browser.constants.BrowserSecurityConstants.DOT_HTML;

/**
 * @author 朱文赵
 * @date 2018/6/24
 * @description
 */
@Slf4j
@RestController
public class BrowserSecurityController {

    /** 获得身份验证请求前的请求路径 */
    private RequestCache requestCache = new HttpSessionRequestCache();

    /** 重定向策略 */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SdkSecurityProperties sdkSecurityProperties;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;


    /**
     * 当需要身份验证的时候，跳转到这里
     *
     * @param request request请求对象
     * @param response response响应对象
     * @return ResultVO.error
     */
    @GetMapping("/authentication/require")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultVO requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String redirectUrl = savedRequest.getRedirectUrl();
            log.info("【浏览器控制器】引发跳转的url为：{}", redirectUrl);
            //如果引发跳转的url是以html结尾的那么直接重定向
            if (StringUtils.endsWithIgnoreCase(redirectUrl, DOT_HTML)) {
                redirectStrategy.sendRedirect(request, response, sdkSecurityProperties.getBrowser().getLoginPage());
            }
        }
        return ResultVO.error(ResultEnums.AUTHENTICATION_REQUIRE);
    }

    /**
     * 从session中拿用户信息
     *
     * @param request
     * @return
     */
    @GetMapping("/social/user")
    public ResultVO<SocialUserInfo> getSocialUserInfo(HttpServletRequest request) {
        Connection<?> connectionFromSession =
                providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        SocialUserInfo socialUserInfo = new SocialUserInfo();
        socialUserInfo.setProviderId(connectionFromSession.getKey().getProviderId());
        socialUserInfo.setProviderUserId(connectionFromSession.getKey().getProviderUserId());
        socialUserInfo.setNickname(connectionFromSession.getDisplayName());
        socialUserInfo.setHeaderImg(connectionFromSession.getImageUrl());
        return ResultVO.success(socialUserInfo);
    }

}
