package com.ximo.spring.security.sdk.core.vo;

import com.ximo.spring.security.sdk.core.enums.ResultEnums;
import lombok.Data;

/**
 * @author 朱文赵
 * @date 2018/6/22
 * @description 统一返回VO对象
 */
@Data
public class ResultVO<T> {

    /** 是否成功*/
    private Boolean success;
    /** 返回码*/
    private Integer code;
    /** 返回信息*/
    private String msg;
    /** 返回数据*/
    private T data;

    /** 私有构造*/
    private ResultVO() {
        throw new UnsupportedOperationException();
    }

    /** 私有构造*/
    private ResultVO(Boolean success, Integer code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /** 私有构造*/
    private ResultVO(Boolean success, ResultEnums enums, T data) {
        this.success = success;
        this.code = enums.getCode();
        this.msg = enums.getMsg();
        this.data = data;
    }

    /** 成功方法 默认返回信息-成功 指定返回数据*/
    public static <T> ResultVO<T> success(T data) {
        return new ResultVO<>(true, ResultEnums.SUCCESS, data);
    }

    /** 成功方法 无返回数据*/
    public static <T> ResultVO<T> success() {
        return new ResultVO<>(true, ResultEnums.SUCCESS, null);
    }

    /** 成功方法 指定返回信息 和 返回数据*/
    public static <T> ResultVO<T> success(String msg, T data) {
        return new ResultVO<>(true, ResultEnums.SUCCESS.getCode(), msg, data);
    }

    /** 错误方法*/
    public static <T> ResultVO<T> error(ResultEnums enums) {
        return new ResultVO<>(false, enums.getCode(), enums.getMsg(), null);
    }

    /** 错误方法*/
    public static <T> ResultVO<T> error(Integer code, String msg) {
        return new ResultVO<>(false, code, msg, null);
    }




}
