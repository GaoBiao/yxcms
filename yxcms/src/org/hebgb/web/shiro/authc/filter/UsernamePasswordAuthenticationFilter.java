package org.hebgb.web.shiro.authc.filter;

import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.hebgb.web.shiro.authc.token.UsernamePasswordToken;
import org.hebgb.web.shiro.captcha.CaptchaException;
import org.hebgb.web.shiro.captcha.ICaptchaFactory;
import org.hebgb.web.shiro.captcha.impl.SampleCaptchaFactory;

public class UsernamePasswordAuthenticationFilter extends AuthenticatingFilter {

	public static final String DEFAULT_USERNAME_PARAM = "username";
	public static final String DEFAULT_PASSWORD_PARAM = "password";
	public static final String DEFAULT_REMEMBER_ME_PARAM = "rememberMe";
	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	public static final String DEFAULT_SESSION_CAPTCHA_KEY = "_captcha_key";
	public static final String DEFAULT_EXCEPTION_ATTRIBUTE = "exception";

	private String usernameParam = DEFAULT_USERNAME_PARAM;
	private String passwordParam = DEFAULT_PASSWORD_PARAM;
	private String rememberMeParam = DEFAULT_REMEMBER_ME_PARAM;
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private Boolean redirectToSaved = true;

	// 验证码设置
	private String captchaUrl = null;
	private int captchaSize = 4;
	private int captchaImageWidth = 70;
	private int captchaImageHeight = 32;
	private int captchaImageFontSize = 26;
	private ICaptchaFactory captchaFactory;

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
		request.setAttribute(DEFAULT_EXCEPTION_ATTRIBUTE, e);
		return true;
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		if (redirectToSaved) {
			WebUtils.redirectToSavedRequest(request, response, getSuccessUrl());
		} else {
			WebUtils.issueRedirect(request, response, getSuccessUrl());
		}
		return true;
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		String username = request.getParameter(usernameParam);
		String password = request.getParameter(passwordParam);
		String captcha = request.getParameter(captchaParam);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		return new UsernamePasswordToken(username, password, rememberMe, host, captcha);
	}

	@Override
	public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		if (isCaptchaRequest(request)) {
			try {
				HttpServletResponse resp = (HttpServletResponse) response;
				String random = RandomStringUtils.randomAlphanumeric(captchaSize);
				((HttpServletRequest) request).getSession().setAttribute(DEFAULT_SESSION_CAPTCHA_KEY, random);
				resp.setHeader("Pragma", "no-cache");
				resp.setHeader("Cache-Control", "no-cache");
				resp.setDateHeader("Expires", 0);
				resp.setContentType("image/jpeg");
				OutputStream out = resp.getOutputStream();
				ImageIO.write(getCaptchaFactory().createImage(captchaImageWidth, captchaImageHeight, captchaImageFontSize, random), "JPEG", out);
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		return super.onPreHandle(request, response, mappedValue);
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if (isLoginRequest(request, response)) {
			if ((request instanceof HttpServletRequest) && WebUtils.toHttp(request).getMethod().equalsIgnoreCase(POST_METHOD)) {
				return executeLogin(request, response);
			} else {
				return true;
			}
		} else {
			saveRequestAndRedirectToLogin(request, response);
		}
		return false;
	}

	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		UsernamePasswordToken token = (UsernamePasswordToken) createToken(request, response);
		if (token == null) {
			String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken " + "must be created in order to execute a login attempt.";
			throw new IllegalStateException(msg);
		}
		try {
			if (StringUtils.isNotEmpty(captchaUrl)) {
				if (StringUtils.isEmpty(token.getCaptcha())) {
					throw new CaptchaException("验证码为空");
				}
				HttpServletRequest req = (HttpServletRequest) request;
				HttpSession session = req.getSession();
				String sessionCaptcha = (String) session.getAttribute(DEFAULT_SESSION_CAPTCHA_KEY);
				session.removeAttribute(DEFAULT_SESSION_CAPTCHA_KEY);
				if (!token.getCaptcha().equalsIgnoreCase(sessionCaptcha)) {
					throw new CaptchaException("验证码错误");
				}
			}
			Subject subject = getSubject(request, response);
			subject.login(token);
			return onLoginSuccess(token, subject, request, response);
		} catch (AuthenticationException e) {
			return onLoginFailure(token, e, request, response);
		}
	}

	private boolean isCaptchaRequest(ServletRequest request) {
		if (StringUtils.isNotEmpty(captchaUrl)) {
			return pathsMatch(captchaUrl, request);
		}
		return false;
	}

	public String getUsernameParam() {
		return usernameParam;
	}

	public void setUsernameParam(String usernameParam) {
		this.usernameParam = usernameParam;
	}

	public String getPasswordParam() {
		return passwordParam;
	}

	public void setPasswordParam(String passwordParam) {
		this.passwordParam = passwordParam;
	}

	public String getRememberMeParam() {
		return rememberMeParam;
	}

	public void setRememberMeParam(String rememberMeParam) {
		this.rememberMeParam = rememberMeParam;
	}

	public String getCaptchaParam() {
		return captchaParam;
	}

	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}

	public Boolean getRedirectToSaved() {
		return redirectToSaved;
	}

	public void setRedirectToSaved(Boolean redirectToSaved) {
		this.redirectToSaved = redirectToSaved;
	}

	public int getCaptchaSize() {
		return captchaSize;
	}

	public void setCaptchaSize(int captchaSize) {
		this.captchaSize = captchaSize;
	}

	public int getCaptchaImageWidth() {
		return captchaImageWidth;
	}

	public void setCaptchaImageWidth(int captchaImageWidth) {
		this.captchaImageWidth = captchaImageWidth;
	}

	public int getCaptchaImageHeight() {
		return captchaImageHeight;
	}

	public void setCaptchaImageHeight(int captchaImageHeight) {
		this.captchaImageHeight = captchaImageHeight;
	}

	public int getCaptchaImageFontSize() {
		return captchaImageFontSize;
	}

	public void setCaptchaImageFontSize(int captchaImageFontSize) {
		this.captchaImageFontSize = captchaImageFontSize;
	}

	public ICaptchaFactory getCaptchaFactory() {
		return captchaFactory == null ? new SampleCaptchaFactory() : captchaFactory;
	}

	public void setCaptchaFactory(ICaptchaFactory captchaFactory) {
		this.captchaFactory = captchaFactory;
	}

	public String getCaptchaUrl() {
		return captchaUrl;
	}

	public void setCaptchaUrl(String captchaUrl) {
		this.captchaUrl = captchaUrl;
	}

}
