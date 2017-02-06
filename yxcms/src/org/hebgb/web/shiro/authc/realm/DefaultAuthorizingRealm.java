package org.hebgb.web.shiro.authc.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hebgb.web.shiro.authc.IShiroUserService;
import org.hebgb.web.shiro.authc.ShiroUser;

public class DefaultAuthorizingRealm extends AuthorizingRealm {

	private IShiroUserService shiroService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		simpleAuthorInfo.addRoles(shiroService.getRoles((ShiroUser) principals.getPrimaryPrincipal()));
		return simpleAuthorInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		ShiroUser principal = shiroService.findByPrincipal((String) token.getPrincipal());
		if (principal != null) {
			if (!principal.isEnabled()) {
				throw new DisabledAccountException("帐号已禁用");
			}
			if (principal.isLocked()) {
				throw new LockedAccountException("帐号已锁定");
			}
			return new SimpleAuthenticationInfo(principal, principal.getPassword(), getName());
		}
		return null;
	}

	public IShiroUserService getShiroService() {
		return shiroService;
	}

	public void setShiroService(IShiroUserService shiroService) {
		this.shiroService = shiroService;
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return true;
	}
}
