package com.ximo.spring.security.sdk.core.validate.code.generator;

import com.ximo.spring.security.sdk.core.validate.code.entity.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author 朱文赵
 * @date 2018/6/30
 * @description 验证码接口类
 */
public interface ValidateCodeGenerator {

    /**
     * 创建ImageCode
     *
     * @param request {@link ServletWebRequest}请求对象
     * @return 图形验证码
     */
    ImageCode generate(ServletWebRequest request);




}
