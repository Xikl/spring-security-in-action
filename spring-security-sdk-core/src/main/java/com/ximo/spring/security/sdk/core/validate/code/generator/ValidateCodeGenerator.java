package com.ximo.spring.security.sdk.core.validate.code.generator;

import com.ximo.spring.security.sdk.core.validate.code.entity.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author 朱文赵
 * @date 2018/6/30
 * @description 验证码接口类
 */
public interface ValidateCodeGenerator {

    /**
     * 创建ValidateCode {@link ValidateCode}
     *
     * @param request {@link ServletWebRequest}请求对象
     * @return 验证码
     */
    ValidateCode generate(ServletWebRequest request);




}
