package org.hebgb.app.cms.controller.admin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hebgb.app.cms.controller.AbstractController;
import org.hebgb.app.cms.model.Admin;
import org.hebgb.web.common.StringEscapeEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public abstract class AbstractAdminController extends AbstractController {
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringEscapeEditor(false, false));
	}

	protected Admin currentAdmin() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.getPrincipal() instanceof Admin) {
			return (Admin) subject.getPrincipal();
		}
		return null;
	}
}
