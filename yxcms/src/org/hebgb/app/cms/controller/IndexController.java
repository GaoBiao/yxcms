package org.hebgb.app.cms.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hebgb.app.cms.model.Article;
import org.hebgb.app.cms.model.IndexModule;
import org.hebgb.app.cms.model.IndexSlider;
import org.hebgb.app.cms.model.Module;
import org.hebgb.app.cms.service.IArticleService;
import org.hebgb.app.cms.service.IIndexModuleService;
import org.hebgb.app.cms.service.IIndexSliderService;
import org.hebgb.app.cms.service.IModuleService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends AbstractFrontController {
	@Autowired
	private IIndexSliderService indexSliderService;
	@Autowired
	private IModuleService moduleService;
	@Autowired
	private IIndexModuleService indexModuleService;
	@Autowired
	private IArticleService articleService;

	@SuppressWarnings("unchecked")
	@RequestMapping("/index.do")
	public void index(Model model) {
		List<IndexSlider> indexSliders = indexSliderService.findAllShow();
		model.addAttribute("indexSliders", indexSliders);
		List<Module> modules = (List<Module>) model.asMap().get("modules");
		List<IndexModule> indexModules = indexModuleService.findAll();
		if (indexModules != null) {
			if (!indexModules.isEmpty()) {
				// 主模块
				IndexModule mainModule = indexModules.remove(0);
				if (StringUtils.isNotEmpty(mainModule.getModuleId())) {
					List<Article> articles = findArticlesByModuleId(mainModule.getModuleId(), 8);
					if (articles != null && !articles.isEmpty()) {
						Article mainArticle = articles.remove(0);
						String content = mainArticle.getArticleContent().replaceAll("<([^>]*)>", "");
						if (content.length() > 160) {
							content = content.substring(0, 160) + "...";
						}
						mainArticle.setArticleContent(content);
						model.addAttribute("mainArticle", mainArticle);
						model.addAttribute("mainArticles", articles);
					}
				}
			}
			if (!indexModules.isEmpty()) {
				// 公告模块
				IndexModule indexModule = indexModules.remove(0);
				indexModule.setModule(findModuleById(indexModule.getModuleId(), modules));
				indexModule.setArticles(findArticlesByModuleId(indexModule.getModuleId(), 12));
				model.addAttribute("noticeModule", indexModule);
			}
			if (!indexModules.isEmpty()) {
				// 服务产品模块
				IndexModule indexModule = indexModules.remove(indexModules.size() - 1);
				indexModule.setModule(findModuleById(indexModule.getModuleId(), modules));
				List<Article> articles = findArticlesByModuleId(indexModule.getModuleId(), 12);
				if (articles != null) {
					for (Article article : articles) {
						String content = article.getArticleContent().replaceAll("<([^>]*)>", "");
						article.setArticleContent(content);
					}
				}
				indexModule.setArticles(articles);
				model.addAttribute("productModule", indexModule);
			}
			if (!indexModules.isEmpty()) {
				for (IndexModule indexModule : indexModules) {
					indexModule.setModule(findModuleById(indexModule.getModuleId(), modules));
					indexModule.setArticles(findArticlesByModuleId(indexModule.getModuleId(), 5));
				}
			}
			model.addAttribute("indexModules", indexModules);
		}
		// 轮播置顶文章
		Article article = new Article();
		article.setIsTop(true);
		List<Order> orders = new ArrayList<>();
		orders.add(Order.desc("createTime"));
		List<Article> topArticles = articleService.findList(article, orders, 0, 10);
		model.addAttribute("topArticles", topArticles);
	}

	private List<Article> findArticlesByModuleId(String moduleId, int size) {
		Article article = new Article();
		article.setModuleId(moduleId);
		List<Order> orders = new ArrayList<>();
		orders.add(Order.desc("isTop"));
		orders.add(Order.desc("createTime"));
		return articleService.findList(article, orders, 0, size);
	}

	private Module findModuleById(String moduleId, List<Module> modules) {
		if (modules != null && !modules.isEmpty()) {
			for (Module module : modules) {
				if (module.getId().equals(moduleId)) {
					return module;
				}
			}
		}
		return null;
	}
}
