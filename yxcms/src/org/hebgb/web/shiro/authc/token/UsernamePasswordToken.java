package org.hebgb.web.shiro.authc.token;

public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String captcha;

	public UsernamePasswordToken(final String username, final String password) {
		this(username, password, false, null, null);
	}

	public UsernamePasswordToken(final String username, final String password, final String captcha) {
		this(username, password, false, null, captcha);
	}

	public UsernamePasswordToken(final String username, final String password, final boolean rememberMe, final String host, final String captcha) {
		super(username, password != null ? password.toCharArray() : null, rememberMe, host);
		this.captcha = captcha;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

}
