package com.ximo.spring.security.sdk.core.controller;

import com.ximo.spring.security.sdk.core.validate.code.entity.ImageCode;
import com.ximo.spring.security.sdk.core.validate.code.generator.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ximo.spring.security.sdk.core.constants.CommonConstants.SESSION_KEY_IMAGE_CODE;

/**
 * @author 朱文赵
 * @date 2018/6/25
 * @description 验证码控制器
 */
@RestController
public class ValidateCodeController {

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    /** session工具类 */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 验证码逻辑
     *
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @throws IOException IO异常
     */
    @GetMapping("/code/image")
    public void generate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);
        ImageCode imageCode = imageCodeGenerator.generate(servletWebRequest);
        sessionStrategy.setAttribute(servletWebRequest, SESSION_KEY_IMAGE_CODE, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

}
