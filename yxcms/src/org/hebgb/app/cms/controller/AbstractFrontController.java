package org.hebgb.app.cms.controller;

import org.hebgb.app.cms.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public class AbstractFrontController extends AbstractController {

	@Autowired
	protected IModuleService moduleService;

	@ModelAttribute
	private void initModules(Model model) {
		model.addAttribute("modules", moduleService.findAll("-"));
	}
}
