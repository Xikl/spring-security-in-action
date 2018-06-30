package com.ximo.spring.security.sdk.core.validate.code.filter;

import com.ximo.spring.security.sdk.core.config.properties.SdkSecurityProperties;
import com.ximo.spring.security.sdk.core.validate.code.entity.ImageCode;
import com.ximo.spring.security.sdk.core.exception.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.ximo.spring.security.sdk.core.constants.CommonConstants.COMMA;
import static com.ximo.spring.security.sdk.core.constants.CommonConstants.SESSION_KEY_IMAGE_CODE;
import static java.util.stream.Collectors.toSet;

/**
 * @author 朱文赵
 * @date 2018/6/28
 * @description 验证码过滤器,继承#{@link OncePerRequestFilter}保证该过滤器只会被执行一次
 */
public class ValidateCodeFilter extends OncePerRequestFilter {

    /** 存放需要拦截的url */
    private Set<String> interceptUrlsSet = new HashSet<>();

    /** ant路径匹配器 */
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /** Sdk安全配置 */
    private SdkSecurityProperties sdkSecurityProperties;

    /** 验证失败处理器 */
    private AuthenticationFailureHandler authenticationFailureHandler;

    /** session工具类 */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    public ValidateCodeFilter(AuthenticationFailureHandler authenticationFailureHandler, SdkSecurityProperties sdkSecurityProperties) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.sdkSecurityProperties = sdkSecurityProperties;
    }

    /**
     * 调用 {@link InitializingBean#afterPropertiesSet()}方法
     * 初始化需要拦截的urlSet 赋值需要拦截的url给{@link #interceptUrlsSet}
     *
     * @throws ServletException servlet异常
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        //获得需要拦截的url的数组 可能为空
        Optional<String[]> configUrlsOptional = Optional.ofNullable(StringUtils
                .splitByWholeSeparatorPreserveAllTokens(sdkSecurityProperties.getCode().getImage().getInterceptUrl(), COMMA));
        //不为空则添加
        configUrlsOptional.ifPresent(configUrl -> interceptUrlsSet.addAll(Arrays.asList(configUrl)));
        //添加登录url
        interceptUrlsSet.add("/authentication/form");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //判断是否需要拦截
        boolean interceptor = interceptUrlsSet.stream().anyMatch(url -> antPathMatcher.match(url, request.getRequestURI()));
        //如果需要过滤
        if (interceptor) {
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                //出现错误直接返回
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request, SESSION_KEY_IMAGE_CODE);

        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }
        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (codeInSession.isExpire()) {
            sessionStrategy.removeAttribute(request, SESSION_KEY_IMAGE_CODE);
            throw new ValidateCodeException("验证码已经过期");
        }
        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }
        sessionStrategy.removeAttribute(request, SESSION_KEY_IMAGE_CODE);
    }
}
