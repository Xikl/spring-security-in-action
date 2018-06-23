package com.ximo.spring.security.sdk.demo.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author 朱文赵
 * @date 2018/6/23
 * @description 一个假设为第三方的过滤器，采用#{@link com.ximo.spring.security.sdk.demo.config.WebFilterConfig}的方式来注册
 */
@Slf4j
public class SdkFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("sdk filter start");
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("sdk filter end");
    }

    @Override
    public void destroy() {

    }
}
