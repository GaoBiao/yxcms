package org.hebgb.web.shiro.captcha;

import java.awt.image.BufferedImage;

public interface ICaptchaFactory {
	public BufferedImage createImage(int width, int height, int fontSize, String content);
}
