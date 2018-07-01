package com.ximo.spring.security.sdk.core.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 朱文赵
 * @date 2018/7/1
 * @description 自定义封装的获得servlet的相关东西工具类 只能在控制层调用
 */
public class ServletUtils {

    private ServletUtils() {
        throw new UnsupportedOperationException();
    }

    public static ServletWebRequest getServletWebRequest() {
        return getServletWebRequest(getServletRequestAttributes());
    }

    private static ServletWebRequest getServletWebRequest(ServletRequestAttributes attributes) {
        return new ServletWebRequest(getRequest(attributes), getResponse(attributes));
    }

    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    private static HttpServletRequest getRequest(ServletRequestAttributes servletRequestAttributes) {
        return servletRequestAttributes.getRequest();
    }

    private static HttpServletResponse getResponse(ServletRequestAttributes servletRequestAttributes) {
        return servletRequestAttributes.getResponse();
    }

}
