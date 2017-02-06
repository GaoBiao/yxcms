package org.hebgb.app.cms.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.hebgb.app.cms.commons.AjaxResult;
import org.hebgb.app.cms.commons.Constants;
import org.hebgb.app.cms.commons.exception.MessageException;
import org.hebgb.app.cms.model.IndexModule;
import org.hebgb.app.cms.model.Module;
import org.hebgb.app.cms.service.IIndexModuleService;
import org.hebgb.app.cms.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("admin_IndexModuleController")
@RequestMapping("/admin/indexModule")
public class IndexModuleController extends AbstractAdminController {
	@Autowired
	private IIndexModuleService indexModuleService;
	@Autowired
	private IModuleService moduleService;

	@RequestMapping("/list.do")
	public void list(Model model) {
		List<IndexModule> indexModules = indexModuleService.findAll();
		if (indexModules != null && !indexModules.isEmpty()) {
			for (IndexModule indexModule : indexModules) {
				indexModule.setModule(moduleService.findById(indexModule.getModuleId()));
			}
			model.addAttribute("indexModules", indexModules);
		}

		List<Module> modules = moduleService.findAll("-");
		List<Module> list = new ArrayList<>();
		if (modules != null && !modules.isEmpty()) {
			for (Module module : modules) {
				boolean flag = true;
				if (indexModules != null && !indexModules.isEmpty()) {
					for (IndexModule indexModule : indexModules) {
						if (module.getId().equals(indexModule.getModuleId())) {
							flag = false;
							break;
						}
					}
				}
				if (flag) {
					list.add(module);
				}
			}
		}
		model.addAttribute("modules", list);
	}

	@PostMapping("/save.do")
	public String save(Model model, IndexModule indexModule) {
		try {
			indexModuleService.save(indexModule);
		} catch (MessageException e) {
			model.addAttribute(Constants.ERROR_MESSAGE, e.getMessage());
		} catch (Exception e) {
			model.addAttribute(Constants.ERROR_MESSAGE, "保存失败");
		}
		return "redirect:list.do";
	}

	@RequestMapping("/move.do")
	@ResponseBody
	public AjaxResult move(String id, String position) {
		AjaxResult result = new AjaxResult();
		try {
			if ("up".equalsIgnoreCase(position)) {
				indexModuleService.moveUp(id);
			} else {
				indexModuleService.moveDown(id);
			}
		} catch (Exception e) {
			result.setCode(AjaxResult.DEFAULT_ERROR_CODE);
			result.setMessage("移动失败");
		}
		return result;
	}

	@RequestMapping("/delete.do")
	@ResponseBody
	public AjaxResult delete(String id) {
		AjaxResult result = new AjaxResult();
		indexModuleService.deleteById(id);
		return result;
	}
}
