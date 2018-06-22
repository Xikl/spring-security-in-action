package com.ximo.spring.security.sdk.core.exception;

import com.ximo.spring.security.sdk.core.enums.ResultEnums;
import lombok.Getter;

/**
 * @author 朱文赵
 * @date 2018/6/22
 * @description 自定义异常
 */
@Getter
public class SpringSecuritySdkException extends RuntimeException {

    /**  错误code码*/
    private Integer code;

    public SpringSecuritySdkException(ResultEnums resultEnums, Throwable cause) {
        super(resultEnums.getMsg(), cause);
        this.code = resultEnums.getCode();
    }

    public SpringSecuritySdkException(ResultEnums resultEnums) {
        super(resultEnums.getMsg());
        this.code = resultEnums.getCode();
    }

}
