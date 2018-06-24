package com.ximo.spring.security.sdk.annotation;

import com.ximo.spring.security.sdk.validator.NameNotRepeatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 朱文赵
 * @date 2018/6/23
 * @description
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NameNotRepeatValidator.class})
public @interface NameNotRepeat {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
