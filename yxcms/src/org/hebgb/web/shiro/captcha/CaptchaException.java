package org.hebgb.web.shiro.captcha;

import org.apache.shiro.authc.AuthenticationException;

public class CaptchaException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CaptchaException() {

	}

	public CaptchaException(String message) {
		super(message);
	}
}
