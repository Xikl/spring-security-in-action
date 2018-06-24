package com.ximo.spring.security.sdk.core.controller;

import com.ximo.spring.security.sdk.core.validate.code.ImageCode;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.ximo.spring.security.sdk.core.constants.CommonConstants.SESSION_KEY;

/**
 * @author 朱文赵
 * @date 2018/6/25
 * @description 验证码控制器
 */
@RestController
public class ValidateCodeController {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();



    @GetMapping("/code/image")
    public void createCodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ImageCode imageCode = createCodeImage(request);
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    private ImageCode createCodeImage(HttpServletRequest request) {
        return null;
    }


}
