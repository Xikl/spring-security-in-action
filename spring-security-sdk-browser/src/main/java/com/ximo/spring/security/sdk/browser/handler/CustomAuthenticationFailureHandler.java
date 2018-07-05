package com.ximo.spring.security.sdk.browser.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ximo.spring.security.sdk.core.config.properties.SdkSecurityProperties;
import com.ximo.spring.security.sdk.core.enums.LoginResponseTypeEnums;
import com.ximo.spring.security.sdk.core.enums.ResultEnums;
import com.ximo.spring.security.sdk.core.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 朱文赵
 * @date 2018/6/28
 * @description
 */
@Slf4j
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SdkSecurityProperties sdkSecurityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
                                    throws IOException, ServletException {
        log.error("登录失败");
        //如果登录返回类型为JSON
        if (LoginResponseTypeEnums.JSON.equals(sdkSecurityProperties.getBrowser().getLoginType())) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            //返回ResultVO.error()对象
            response.getWriter().write(objectMapper.writeValueAsString(ResultVO.error(ResultEnums.LOGIN_ERROR.getCode(),
                    exception.getMessage())));
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
