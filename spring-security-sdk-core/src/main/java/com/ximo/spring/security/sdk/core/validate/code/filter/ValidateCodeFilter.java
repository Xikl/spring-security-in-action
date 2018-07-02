package com.ximo.spring.security.sdk.core.validate.code.filter;

import com.ximo.spring.security.sdk.core.config.properties.SdkSecurityProperties;
import com.ximo.spring.security.sdk.core.enums.ValidateCodeTypeEnums;
import com.ximo.spring.security.sdk.core.exception.ValidateCodeException;
import com.ximo.spring.security.sdk.core.validate.code.holder.ValidateCodeProcessorHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.ximo.spring.security.sdk.core.constants.CommonConstants.COMMA;
import static com.ximo.spring.security.sdk.core.constants.SecurityConstants.*;

/**
 * @author 朱文赵
 * @date 2018/6/28
 * @description 验证码过滤器,继承#{@link OncePerRequestFilter}保证该过滤器只会被执行一次
 */
@Slf4j
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    /** ant路径匹配器 */
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /** Sdk安全配置 */
    @Autowired
    private SdkSecurityProperties sdkSecurityProperties;

    /** 验证失败处理器 */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /** 存放所有需要验证的验证码的url */
    private Map<String, ValidateCodeTypeEnums> urlMap = new HashMap<>();


    /**
     * 调用 {@link InitializingBean#afterPropertiesSet()}方法
     * 初始化需要拦截的urlSet 赋值需要拦截的url给urlMap
     *
     * @throws ServletException servlet异常
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        //添加拦截配置信息前端固定url
        urlMap.put(DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeTypeEnums.IMAGE);
        urlMap.put(DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeTypeEnums.SMS);

        //添加拦截用户自定义需要拦截的url
        addUrlToMap(sdkSecurityProperties.getCode().getImage().getInterceptUrl(), ValidateCodeTypeEnums.IMAGE);
        addUrlToMap(sdkSecurityProperties.getCode().getSms().getInterceptUrl(), ValidateCodeTypeEnums.SMS);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            ValidateCodeTypeEnums validateCodeTypeEnums = getValidateCodeTypeEnums(request);
            if (validateCodeTypeEnums != null) {
                log.info("校验请求[{}]中的验证码类型为{}", request.getRequestURI(), validateCodeTypeEnums);
                validateCodeProcessorHolder.findValidateCodeProcessor(validateCodeTypeEnums)
                        .validate(new ServletWebRequest(request, response));
            }
        } catch (ValidateCodeException e) {
            authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            //失败直接返回，不要去调下一个过滤器
            return;
        }

        filterChain.doFilter(request, response);
    }

    private ValidateCodeTypeEnums getValidateCodeTypeEnums(HttpServletRequest request) {
        AtomicReference<ValidateCodeTypeEnums> result = new AtomicReference<>();
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), GET_METHOD)) {
            urlMap.forEach((url, typeEnums) -> {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    result.set(typeEnums);
                }
            });
        }
        return result.get();
    }


    protected void addUrlToMap(String urlString, ValidateCodeTypeEnums validateCodeTypeEnums) {
        if (StringUtils.isNoneBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, COMMA);
            Arrays.stream(urls).forEach(url -> urlMap.put(url, validateCodeTypeEnums));
        }
    }

}
