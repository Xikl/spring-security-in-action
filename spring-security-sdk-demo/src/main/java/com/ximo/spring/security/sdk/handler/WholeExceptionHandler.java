package com.ximo.spring.security.sdk.handler;

import com.ximo.spring.security.sdk.core.exception.SpringSecuritySdkException;
import com.ximo.spring.security.sdk.core.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 朱文赵
 * @date 2018/6/23
 * @description
 */
@Slf4j
@RestControllerAdvice
public class WholeExceptionHandler {


    /**
     * 全局异常-捕获#{@link Exception}信息
     *
     * @param e Exception
     * @return ResultVO.error(HttpStatus.INTERNAL_SERVER_ERROR)
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public ResultVO handleException(Exception e) {
        log.error("【全局异常】未捕获异常信息", e);
        return ResultVO.error(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * 全局异常-捕获#{@link SpringSecuritySdkException}自定义异常
     *
     * @param e SpringSecuritySdkException
     * @return ResultVO.error(e.getCode(), e.getMessage());
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = SpringSecuritySdkException.class)
    public ResultVO handleSpringSecuritySdkException(SpringSecuritySdkException e) {
        log.error("【全局异常】已知异常信息", e);
        return ResultVO.error(e.getCode(), e.getMessage());
    }

    /**
     * 全局异常-捕获#{@link MissingServletRequestParameterException}请求方法缺少必要参数错误
     *
     * @param e MissingServletRequestParameterException
     * @return ResultVO.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResultVO handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("【全局异常】请求方法缺少必要参数", e);
        return ResultVO.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    /**
     * 全局异常-捕获#{@link HttpRequestMethodNotSupportedException}请求方法不支持
     *
     * @param e HttpRequestMethodNotSupportedException
     * @return ResultVO.error(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResultVO handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("【全局异常】请求方法不支持", e);
        return ResultVO.error(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
    }



}
