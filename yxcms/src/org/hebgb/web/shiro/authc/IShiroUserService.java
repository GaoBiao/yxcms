package org.hebgb.web.shiro.authc;

import java.util.List;

public interface IShiroUserService {
	public ShiroUser findByPrincipal(Object principal);

	public List<String> getRoles(ShiroUser shiroUser);
}
