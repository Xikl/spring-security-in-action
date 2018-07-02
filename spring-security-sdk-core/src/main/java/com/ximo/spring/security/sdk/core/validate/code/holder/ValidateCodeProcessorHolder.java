package com.ximo.spring.security.sdk.core.validate.code.holder;

import com.ximo.spring.security.sdk.core.enums.ValidateCodeTypeEnums;
import com.ximo.spring.security.sdk.core.exception.ValidateCodeException;
import com.ximo.spring.security.sdk.core.validate.code.processor.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * @author 朱文赵
 * @date 2018/7/2
 * @description
 */
@Component
public class ValidateCodeProcessorHolder {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessorMap;

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeTypeEnums typeEnums) {
        return findValidateCodeProcessor(typeEnums.toString().toLowerCase());
    }


    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String processorName = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        Optional<ValidateCodeProcessor> validateCodeProcessorOptional =
                Optional.ofNullable(validateCodeProcessorMap.get(processorName));
        return validateCodeProcessorOptional
                .orElseThrow(() -> new ValidateCodeException("验证码处理器" + processorName + "不存在"));
    }

}
