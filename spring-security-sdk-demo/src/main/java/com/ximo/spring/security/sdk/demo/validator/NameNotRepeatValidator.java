package com.ximo.spring.security.sdk.demo.validator;

import com.ximo.spring.security.sdk.demo.annotation.NameNotRepeat;
import com.ximo.spring.security.sdk.demo.domain.User;
import com.ximo.spring.security.sdk.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 朱文赵
 * @date 2018/6/23
 * @description
 */
@Slf4j
public class NameNotRepeatValidator implements ConstraintValidator<NameNotRepeat, User> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        return userService.isUsernameExist(user.getUserId(), user.getUsername());
    }

    @Override
    public void initialize(NameNotRepeat constraintAnnotation) {
        log.info("NameNotRepeatValidator init");
    }
}
