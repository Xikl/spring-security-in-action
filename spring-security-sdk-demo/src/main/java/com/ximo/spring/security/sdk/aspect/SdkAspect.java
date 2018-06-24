package com.ximo.spring.security.sdk.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * @author 朱文赵
 * @date 2018/6/23
 * @description sdk切面
 */
@Slf4j
@Aspect
@Component
public class SdkAspect {

    @Pointcut("execution(public * com.ximo.spring.security.sdk.controller.UserController.*(..))")
    public void userAspect(){}

    @Around("userAspect()")
    public Object userAspectAround(ProceedingJoinPoint pjp) throws Throwable {
        log.info("user aspect start");
        Instant start = Instant.now();

        Object result = pjp.proceed();

        Arrays.stream(pjp.getArgs())
                .forEach(System.out::println);

        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        long millis = timeElapsed.toMillis();

        log.info("user aspect end:{}ms", millis);
        return result;
    }


}
