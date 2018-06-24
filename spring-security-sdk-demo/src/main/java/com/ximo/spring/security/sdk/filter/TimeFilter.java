package com.ximo.spring.security.sdk.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/**
 * @author 朱文赵
 * @date 2018/6/23
 * @description
 */
@Slf4j
//@Component
public class TimeFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("time filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("time filter start");

        Instant start = Instant.now();
        filterChain.doFilter(servletRequest, servletResponse);
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        long millis = timeElapsed.toMillis();

        log.info("time filter timeElapsed:{}ms", millis);
        log.info("time filter finish");
    }

    @Override
    public void destroy() {
        log.info("time filter destroy");
    }
}
