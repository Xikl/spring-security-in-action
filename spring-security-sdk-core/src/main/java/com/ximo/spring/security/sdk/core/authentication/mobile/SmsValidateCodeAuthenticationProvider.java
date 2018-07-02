package com.ximo.spring.security.sdk.core.authentication.mobile;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

/**
 * @author 朱文赵
 * @date 2018/7/1
 * @description
 */
public class SmsValidateCodeAuthenticationProvider implements AuthenticationProvider {

    @Getter @Setter
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获得短信验证码的信息
        SmsValidateCodeAuthenticationToken smsValidateCodeAuthenticationToken =
                (SmsValidateCodeAuthenticationToken) authentication;
        String principal = (String) smsValidateCodeAuthenticationToken.getPrincipal();

        //获得用户信息
        Optional<UserDetails> userDetailsOptional = Optional.ofNullable(userDetailsService.loadUserByUsername(principal));
        SmsValidateCodeAuthenticationToken authenticationResult = userDetailsOptional
                .map(userDetails -> new SmsValidateCodeAuthenticationToken(principal, userDetails.getAuthorities()))
                .orElseThrow(() -> new InternalAuthenticationServiceException("无法找到相应的用户信息"));

        authenticationResult.setDetails(smsValidateCodeAuthenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsValidateCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
