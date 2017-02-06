package org.hebgb.app.cms.controller.admin;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.hebgb.app.cms.commons.AjaxResult;
import org.hebgb.app.cms.commons.Constants;
import org.hebgb.app.cms.commons.enums.ObjectStatus;
import org.hebgb.app.cms.commons.exception.MessageException;
import org.hebgb.app.cms.model.Admin;
import org.hebgb.app.cms.model.Admin.RoleType;
import org.hebgb.app.cms.service.IAdminService;
import org.hebgb.web.common.Page;
import org.hebgb.web.common.Paged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("admin_AdminController")
@RequestMapping("/admin")
public class AdminController extends AbstractAdminController {
	@Autowired
	private IAdminService adminService;

	@GetMapping("/login.do")
	public void login() {
	}

	@PostMapping("/login.do")
	public void loginSuccess(Model model) {
		Exception exception = (Exception) request.getAttribute("exception");
		if (exception != null) {
			String message = getI18nValue(exception.getClass().getName());
			model.addAttribute(Constants.ERROR_MESSAGE, message);
		}
		if (currentAdmin() != null) {
			adminService.updateLastLoginTime(currentAdmin().getId(), new Date());
		}
	}

	@RequestMapping("/logout.do")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "redirect:login.do";
	}

	@RequestMapping("/init.do")
	public String init(Model model, String password, String loginName) {
		if (adminService.getAllCount() > 0) {
			return "redirect:/admin/login.do";
		}
		if ("POST".equals(request.getMethod())) {
			try {
				Admin admin = new Admin();
				admin.setLoginName(loginName);
				admin.setPassword(password);
				admin.setRole(RoleType.admin);
				adminService.save(admin);
				return "redirect:/admin/login.do";
			} catch (MessageException e) {
				model.addAttribute("error_message", e.getMessage());
			} catch (Exception e) {
				model.addAttribute("error_message", "初始化失败");
			}
		}
		return "/admin/init";
	}

	@RequestMapping("/list.do")
	public void list(Model model, Admin admin, Page page, String startDate, String endDate) {
		saveSessionParams();
		Date startTime = null;
		Date endTime = null;
		if (StringUtils.isNotEmpty(startDate)) {
			try {
				startTime = DateUtils.parseDate(startDate, "yyyy-MM-dd");
			} catch (ParseException e) {
			}
		}
		if (StringUtils.isNotEmpty(endDate)) {
			try {
				endTime = DateUtils.parseDate(endDate, "yyyy-MM-dd");
			} catch (ParseException e) {
			}
		}
		Paged<Admin> paged = adminService.findAll(page, admin, startTime, endTime);
		model.addAttribute("paged", paged);
		model.addAttribute("statuses", getEnumI18nMap(ObjectStatus.values()));
		model.addAttribute("admin", admin);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
	}

	@RequestMapping("/edit.do")
	public void edit(Model model, Admin admin) {
		if (StringUtils.isNotEmpty(admin.getId())) {
			admin = adminService.findById(admin.getId());
		}
		model.addAttribute("admin", admin);
	}

	@PostMapping("/save.do")
	public String save(Model model, Admin admin) {
		try {
			adminService.save(admin);
			model.addAllAttributes(getSessionParams());
			return "redirect:list.do";
		} catch (MessageException e) {
			model.addAttribute(Constants.ERROR_MESSAGE, e.getMessage());
		} catch (Exception e) {
			model.addAttribute(Constants.ERROR_MESSAGE, "保存失败");
		}
		model.addAttribute("admin", admin);
		return "/admin/edit";
	}

	@RequestMapping("/delete.do")
	@ResponseBody
	public AjaxResult delete(String id) {
		AjaxResult result = new AjaxResult();
		if (StringUtils.isNotEmpty(id)) {
			adminService.delete(id);
		}
		return result;
	}
}
