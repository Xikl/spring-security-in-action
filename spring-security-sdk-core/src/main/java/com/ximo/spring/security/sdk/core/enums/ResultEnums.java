package com.ximo.spring.security.sdk.core.enums;

import lombok.Getter;

/**
 * @author 朱文赵
 * @date 2018/6/22
 * @description
 */
@Getter
public enum ResultEnums {

    /** 成功返回码*/
    SUCCESS(0, "成功"),
    INNER_ERROR(-1, "内部错误"),
    AUTHORITY_NOT_FOUND(1, "没有该权限"),
    RESOURCE_NOT_FOUND(2, "哎呦，你所查找的好像消失了"),
    ;


    private Integer code;

    private String msg;

    ResultEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
