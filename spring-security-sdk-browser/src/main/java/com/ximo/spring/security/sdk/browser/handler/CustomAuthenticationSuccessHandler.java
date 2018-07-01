package com.ximo.spring.security.sdk.browser.handler;

import com.ximo.spring.security.sdk.core.config.properties.SdkSecurityProperties;
import com.ximo.spring.security.sdk.core.enums.LoginResponseTypeEnums;
import com.ximo.spring.security.sdk.core.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 朱文赵
 * @date 2018/6/28
 * @description 自定义权限控制处理器
 */
@Slf4j
@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /** json读写操作类 */
    @Autowired
    private ObjectMapper objectMapper;

    /** 安全配置项 */
    @Autowired
    private SdkSecurityProperties sdkSecurityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                                                                                    throws ServletException, IOException {
        log.info("登录成功");
        if (LoginResponseTypeEnums.JSON.equals(sdkSecurityProperties.getBrowser().getLoginType())) {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(ResultVO.success(authentication)));
        } else {
            //重定向
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
