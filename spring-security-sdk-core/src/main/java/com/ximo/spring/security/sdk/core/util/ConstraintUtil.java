package com.ximo.spring.security.sdk.core.util;

import com.ximo.spring.security.sdk.core.enums.ResultEnums;
import com.ximo.spring.security.sdk.core.exception.SpringSecuritySdkException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import static com.ximo.spring.security.sdk.core.constants.CommonConstants.SEMICOLON;
import static java.util.stream.Collectors.joining;

/**
 * @author 朱文赵
 * @date 2018/6/23
 * @description 约束工具类
 */
public class ConstraintUtil {

    /** 私有构造方法 */
    private ConstraintUtil() {
        throw new UnsupportedOperationException();
    }

    /** 有异常就抛出 */
    public static void orElseThrow(BindingResult errors) {
        if (errors.hasErrors()) {
            String errorMsg = errors.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .distinct()
                    .collect(joining(SEMICOLON));
            throw new SpringSecuritySdkException(ResultEnums.CONSTRAINT_ERROR.getCode(), errorMsg);
        }
    }

}
