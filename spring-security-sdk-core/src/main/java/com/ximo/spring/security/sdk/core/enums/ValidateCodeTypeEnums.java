package com.ximo.spring.security.sdk.core.enums;

import lombok.ToString;

import static com.ximo.spring.security.sdk.core.constants.SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
import static com.ximo.spring.security.sdk.core.constants.SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;

/**
 * @author 朱文赵
 * @date 2018/7/1
 * @description
 */
@ToString
public enum ValidateCodeTypeEnums {
    /** 短信验证码 */
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },

    /** 图片验证码 */
    IMAGE{
        @Override
        public String getParamNameOnValidate() {
            return DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    },
    ;

    /**
     * 获得验证码的名字
     *
     * @return 验证码的名字
     */
    public abstract String getParamNameOnValidate();



}
