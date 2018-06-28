package com.ximo.spring.security.sdk.core.controller;

import com.ximo.spring.security.sdk.core.entity.validate.code.ImageCode;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import static com.ximo.spring.security.sdk.core.constants.CommonConstants.SESSION_KEY_IMAGE_CODE;

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
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY_IMAGE_CODE, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    /**
     * 创建codeImage
     *
     * @param request
     * @return
     */
    private ImageCode createCodeImage(HttpServletRequest request) {
        int width = 67;
        int height = 23;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        //生成干扰条纹
        Random random = new Random();
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Timee New Roman", Font.ITALIC, 20));
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
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand.append(rand);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110),
                    20 + random.nextInt(100)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();
        return new ImageCode(image, sRand.toString(), 60);
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
