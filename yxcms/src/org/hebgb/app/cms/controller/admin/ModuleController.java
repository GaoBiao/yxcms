package org.hebgb.app.cms.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hebgb.app.cms.commons.AjaxResult;
import org.hebgb.app.cms.commons.Constants;
import org.hebgb.app.cms.commons.exception.MessageException;
import org.hebgb.app.cms.model.Article;
import org.hebgb.app.cms.model.Module;
import org.hebgb.app.cms.service.IArticleService;
import org.hebgb.app.cms.service.IModuleService;
import org.hebgb.web.common.Page;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("admin_ModuleController")
@RequestMapping("/admin/module")
public class ModuleController extends AbstractAdminController {
	@Autowired
	private IModuleService moduleService;
	@Autowired
	private IArticleService articleService;

	@RequestMapping("/list.do")
	public void list(Model model, Module module, Page page) {
		List<Module> modules = moduleService.findAll("-");
		if (modules != null && !modules.isEmpty()) {
			model.addAttribute("modules", modules);
		}
	}

	@GetMapping("/edit.do")
	public void edit(Model model, Module module) {
		if (StringUtils.isNotEmpty(module.getId())) {
			module = moduleService.findById(module.getId());
		} else {
			module.setIsMenu(true);
		}
		if (StringUtils.isNotEmpty(module.getParentId())) {
			Module parent = moduleService.findById(module.getParentId());
			module.setParent(parent);
		}
		model.addAttribute("module", module);
	}

	@PostMapping("/save.do")
	public String save(Model model, Module module) {
		try {
			moduleService.save(module);
		} catch (MessageException e) {
			model.addAttribute(Constants.ERROR_MESSAGE, e.getMessage());
		} catch (Exception e) {
			model.addAttribute(Constants.ERROR_MESSAGE, "保存失败");
		}
		model.addAttribute("module", module);
		return "redirect:list.do";
	}

	@RequestMapping("/delete.do")
	@ResponseBody
	public AjaxResult delete(String id) {
		AjaxResult result = new AjaxResult();
		if (StringUtils.isNotEmpty(id)) {
			moduleService.deleteById(id);
		}
		return result;
	}

	@RequestMapping("/move.do")
	@ResponseBody
	public AjaxResult move(String id, String position) {
		AjaxResult result = new AjaxResult();
		if (StringUtils.isNotEmpty(id)) {
			if ("up".equalsIgnoreCase(position)) {
				moduleService.moveUp(id);
			} else {
				moduleService.moveDown(id);
			}
		}
		return result;
	}

	@RequestMapping("/searchModule.do")
	@ResponseBody
	public List<Map<String, String>> searchModule(String term) {
		List<Map<String, String>> list = new ArrayList<>();
		if (StringUtils.isNotEmpty(term)) {
			List<Module> modules = moduleService.findAll("-");
			for (Module module : modules) {
				if (module.getName().indexOf(term) != -1) {
					Map<String, String> map = new HashMap<>();
					map.put("label", module.getIndent() + module.getName());
					map.put("value", module.getId());
					list.add(map);
				}
			}
		}
		return list;
	}

	@RequestMapping("/searchArticle.do")
	@ResponseBody
	public List<Map<String, String>> searchArticle(String term) {
		List<Map<String, String>> list = new ArrayList<>();
		if (StringUtils.isNotEmpty(term)) {
			Article article = new Article();
			article.setArticleTitle(term);
			List<Order> orders = new ArrayList<>();
			orders.add(Order.desc("createTime"));
			List<Article> articles = articleService.findList(article, orders, 0, 10);
			for (Article a : articles) {
				Map<String, String> map = new HashMap<>();
				map.put("label", a.getArticleTitle());
				map.put("value", a.getId());
				list.add(map);
			}
		}
		return list;
	}
}
