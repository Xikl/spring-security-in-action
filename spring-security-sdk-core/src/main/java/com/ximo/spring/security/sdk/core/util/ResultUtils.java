package com.ximo.spring.security.sdk.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 朱文赵
 * @date 2018/6/22
 * @description
 */
public class ResultUtils {

    /** 私有构造 */
    private ResultUtils() {
        throw new UnsupportedOperationException();
    }

    /** 返回只有一个值的key value json */
    public static <T> Map<String, T> one(String key, T value) {
        Map<String, T> result = new HashMap<>(1);
        result.put(key, value);
        return result;
    }
}
