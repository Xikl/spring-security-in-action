package com.ximo.spring.security.sdk.core.authentication.mobile;

import static com.ximo.spring.security.sdk.core.constants.SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE;
import static com.ximo.spring.security.sdk.core.constants.SecurityConstants.POST_METHOD;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ximo.spring.security.sdk.core.constants.SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;

/**
 * @author 朱文赵
 * @date 2018/7/1
 * @description
 */
public class SmsValidateCodeAuthenticationFilter extends
        AbstractAuthenticationProcessingFilter {
    // ~ Static fields/initializers
    // =====================================================================================


    private String mobileParameter = DEFAULT_PARAMETER_NAME_MOBILE;
    private boolean postOnly = true;

    // ~ Constructors
    // ===================================================================================================

    public SmsValidateCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher(DEFAULT_LOGIN_PROCESSING_URL_MOBILE, POST_METHOD));
    }

    // ~ Methods
    // ========================================================================================================

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !POST_METHOD.equals(request.getMethod())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String mobile = obtainMobile(request);

        if (mobile == null) {
            mobile = "";
        }

        mobile = mobile.trim();

        SmsValidateCodeAuthenticationToken authRequest = new SmsValidateCodeAuthenticationToken(mobile);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }


    /**
     * Enables subclasses to override the composition of the username, such as by
     * including additional values and a separator.
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the username that will be presented in the <code>Authentication</code>
     * request token to the <code>AuthenticationManager</code>
     */
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     * set
     */
    protected void setDetails(HttpServletRequest request,
                              SmsValidateCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Sets the parameter name which will be used to obtain the username from the login
     * request.
     *
     * @param mobileParameter the parameter name. Defaults to "username".
     */
    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "Username parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }


    /**
     * Defines whether only HTTP POST requests will be allowed by this filter. If set to
     * true, and an authentication request is received which is not a POST request, an
     * exception will be raised immediately and authentication will not be attempted. The
     * <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
     * authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return mobileParameter;
    }

}

