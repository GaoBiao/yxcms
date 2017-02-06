package org.hebgb.web.shiro.authc;


public abstract class ShiroUser {

	public abstract String getPassword();

	public boolean isEnabled() {
		return true;
	}

	public boolean isLocked() {
		return false;
	}
}
