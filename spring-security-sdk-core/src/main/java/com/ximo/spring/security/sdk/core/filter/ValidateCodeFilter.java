package com.ximo.spring.security.sdk.core.filter;

import com.ximo.spring.security.sdk.core.entity.validate.code.ImageCode;
import com.ximo.spring.security.sdk.core.exception.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ximo.spring.security.sdk.core.constants.CommonConstants.SESSION_KEY_IMAGE_CODE;

/**
 * @author 朱文赵
 * @date 2018/6/28
 * @description 验证码过滤器,继承#{@link OncePerRequestFilter}保证该过滤器只会被执行一次
 */
public class ValidateCodeFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    public ValidateCodeFilter(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (StringUtils.equals("/authentication/form", request.getRequestURI())
                && StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {
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
