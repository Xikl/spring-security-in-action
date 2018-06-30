package com.ximo.spring.security.sdk.core.validate.code.generator;

import com.ximo.spring.security.sdk.core.config.properties.ImageCodeProperties;
import com.ximo.spring.security.sdk.core.config.properties.SdkSecurityProperties;
import com.ximo.spring.security.sdk.core.validate.code.entity.ImageCode;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author 朱文赵
 * @date 2018/6/30
 * @description 图形验证码
 */
public class ImageCodeGenerator implements ValidateCodeGenerator {

    private SdkSecurityProperties sdkSecurityProperties;

    public ImageCodeGenerator(SdkSecurityProperties sdkSecurityProperties) {
        this.sdkSecurityProperties = sdkSecurityProperties;
    }

    /**
     * 创建codeImage
     *
     * @param request {@link ServletWebRequest}请求对象
     * @return 图形验证码
     */
    @Override
    public ImageCode generate(ServletWebRequest request) {
        //图片验证码配置对象
        ImageCodeProperties imageCodeProperties = sdkSecurityProperties.getCode().getImage();

        int width = ServletRequestUtils
                .getIntParameter(request.getRequest(), "width", imageCodeProperties.getWidth());
        int height = ServletRequestUtils
                .getIntParameter(request.getRequest(), "height", imageCodeProperties.getHeight());

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        //生成干扰条纹
        Random random = new Random();
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        //生成四位随机验证码
        StringBuilder sRand = new StringBuilder();
        //验证码长度
        int imageCodeLength = imageCodeProperties.getLength();
        for (int i = 0; i < imageCodeLength; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand.append(rand);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110),
                    20 + random.nextInt(100)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();
        return new ImageCode(image, sRand.toString(), imageCodeProperties.getExpireIn());
    }

    /**
     * 生成随机条纹
     *
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();

        if (fc > 255) {
            fc = 255;
        }

        if (bc > 255) {
            bc = 255;
        }

        int result = bc - fc;
        int r = fc + random.nextInt(result);
        int g = fc + random.nextInt(result);
        int b = fc + random.nextInt(result);
        return new Color(r, g, b);
    }
}
