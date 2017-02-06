package org.hebgb.app.cms.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hebgb.app.cms.commons.exception.MessageException;
import org.hebgb.app.cms.model.Article;
import org.hebgb.app.cms.model.Module;
import org.hebgb.app.cms.service.IArticleService;
import org.hebgb.app.cms.service.IModuleService;
import org.hebgb.web.common.Page;
import org.hebgb.web.common.Paged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/article")
public class ArticleController extends AbstractFrontController {
	@Autowired
	private IArticleService articleService;
	@Autowired
	private IModuleService moduleService;

	private List<Module> initBreadcrumbs(List<Module> modules, String moduleId) {
		List<Module> list = new ArrayList<>();
		if (modules != null && !modules.isEmpty()) {
			Module currentModule = null;
			for (Module module : modules) {
				if (module.getId().equals(moduleId)) {
					currentModule = module;
					break;
				}
			}
			if (currentModule != null) {
				list.add(currentModule);
				String pid = currentModule.getParentId();
				while (StringUtils.isNotEmpty(pid)) {
					for (Module module : modules) {
						if (module.getId().equals(pid)) {
							list.add(0, module);
							pid = module.getParentId();
							break;
						}
					}
				}
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/list.do")
	public void list(Model model, String moduleId, String query, Page page) {
		Paged<Article> paged = articleService.search(moduleId, query, page);
		model.addAttribute("paged", paged);
		model.addAttribute("moduleId", moduleId);
		List<Module> modules = (List<Module>) model.asMap().get("modules");
		List<Module> breadcrumbs = initBreadcrumbs(modules, moduleId);
		model.addAttribute("breadcrumbs", breadcrumbs);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view.do")
	public void view(Model model, String id) {
		Article article = articleService.findById(id);
		if (article != null && article.getIsPublish()) {
			article.setViewCount(article.getViewCount() + 1);
			try {
				articleService.save(article);
			} catch (MessageException e) {
			}
			model.addAttribute("article", article);
			List<Module> modules = (List<Module>) model.asMap().get("modules");
			List<Module> breadcrumbs = initBreadcrumbs(modules, article.getModuleId());
			model.addAttribute("breadcrumbs", breadcrumbs);
		}
	}
}
