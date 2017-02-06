package org.hebgb.web.shiro.captcha.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.hebgb.web.shiro.captcha.ICaptchaFactory;

public class SampleCaptchaFactory implements ICaptchaFactory {

	@Override
	public BufferedImage createImage(int width, int height, int fontSize, String content) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		// 填充背景
		g.setColor(getRandColor(220, 250));
		g.fillRect(0, 0, width, height);
		// 加入干扰线条
		for (int i = 0; i < 8; i++) {
			g.setColor(getRandColor(40, 150));
			Random random = new Random();
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(height);
			g.drawLine(x, y, x1, y1);
		}
		String[] fontTypes = { "Arial", "Arial Black", "AvantGarde Bk BT", "Calibri" };
		Random random = new Random();
		int x = 2;
		char[] cs = content.toCharArray();
		for (char r : cs) {
			int y = random.nextInt(height - fontSize) + fontSize;
			g.setColor(new Color(50 + random.nextInt(100), 50 + random.nextInt(100), 50 + random.nextInt(100)));
			g.setFont(new Font(fontTypes[random.nextInt(fontTypes.length)], Font.BOLD, fontSize));
			g.drawString(String.valueOf(r), x, y);
			x += width / cs.length;
		}
		return image;
	}

	private Color getRandColor(int fc, int bc) {
		int f = fc;
		int b = bc;
		Random random = new Random();
		if (f > 255) {
			f = 255;
		}
		if (b > 255) {
			b = 255;
		}
		return new Color(f + random.nextInt(b - f), f + random.nextInt(b - f), f + random.nextInt(b - f));
	}

}
