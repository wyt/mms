package org.wangyt.mms.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.wangyt.mms.util.security.Coder;

/**
 * 图形验证码工具类
 * 
 * @author WANG YONG TAO
 *
 */
public class CaptchaUtils {

	public static Color getRandColor(int fc, int bc) {

		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public static String randerCaptcha(OutputStream out) throws IOException {

		StringBuilder code = new StringBuilder("");
		int width = 110, height = 40;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();
		Random random = new Random();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		Font f = new Font("Default", Font.PLAIN, 30);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		g.setColor(getRandColor(160, 200));

		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			code.append(rand);
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.setFont(f);
			g.drawString(rand, 23 * i + 12, 30);
		}

		g.dispose();
		ImageIO.write(image, "JPEG", out);
		return code.toString();
	}
	
	public static void main(String[] args) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String code = randerCaptcha(out);
		System.out.println("code: " + code);
		String captcha = Coder.encryptBASE64(out.toByteArray());
		System.out.println("Captcha: \n" + captcha);
	}

}
