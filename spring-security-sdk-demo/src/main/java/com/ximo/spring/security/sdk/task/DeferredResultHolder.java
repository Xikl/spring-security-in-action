package com.ximo.spring.security.sdk.task;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 朱文赵
 * @date 2018/6/24
 * @description
 */
@Component
public class DeferredResultHolder {

    /** STRING_DEFERRED_RESULT_MAP */
    private static final Map<String, DeferredResult<String>> STRING_DEFERRED_RESULT_MAP = new ConcurrentHashMap<>();


    public void put(String orderNumber, DeferredResult<String> result) {
        STRING_DEFERRED_RESULT_MAP.put(orderNumber, result);
    }

    public DeferredResult<String> take(String orderNumber) {
        return STRING_DEFERRED_RESULT_MAP.get(orderNumber);
    }
}
