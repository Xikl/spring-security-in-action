package com.ximo.spring.security.sdk.core.validate.code.processor.impl;

import com.ximo.spring.security.sdk.core.validate.code.entity.ImageCode;
import com.ximo.spring.security.sdk.core.validate.code.processor.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * @author 朱文赵
 * @date 2018/7/1
 * @description
 */
@Component
public class ImageValidateCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws IOException {
        ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
