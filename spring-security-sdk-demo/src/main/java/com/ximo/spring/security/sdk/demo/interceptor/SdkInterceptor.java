package com.ximo.spring.security.sdk.demo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 朱文赵
 * @date 2018/6/23
 * @description 一个拦截器 添加@Component后还需要添加其他的配置
 * 拦截器拿不到参数列表
 */
@Slf4j
@Component
public class SdkInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("sdk interceptor preHandle");
        //添加一个参数
        request.setAttribute("startTime", System.currentTimeMillis());
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        log.info(handlerMethod.getBean().getClass().getName());
        log.info(handlerMethod.getMethod().getName());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("sdk interceptor postHandle");
        long timeElapsed = System.currentTimeMillis() - (long) request.getAttribute("startTime");
        log.info("sdk interceptor timeElapsed:", timeElapsed);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("sdk interceptor afterCompletion");
        long timeElapsed = System.currentTimeMillis() - (long) request.getAttribute("startTime");
        log.info("sdk interceptor timeElapsed:", timeElapsed);
        log.info("sdk interceptor exception", ex);
    }

}
