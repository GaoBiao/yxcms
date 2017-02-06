package org.hebgb.app.cms.controller.admin;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hebgb.app.cms.commons.Constants;
import org.hebgb.app.cms.commons.exception.MessageException;
import org.hebgb.app.cms.model.Article;
import org.hebgb.app.cms.model.Module;
import org.hebgb.app.cms.service.IArticleService;
import org.hebgb.app.cms.service.IModuleService;
import org.hebgb.utils.file.FileUploadUtils;
import org.hebgb.web.common.Page;
import org.hebgb.web.common.Paged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller("admin_ArticleController")
@RequestMapping("/admin/article")
public class ArticleController extends AbstractAdminController {
	@Autowired
	private IArticleService articleService;
	@Autowired
	private IModuleService moduleService;
	@Autowired
	private FileUploadUtils imageUploadUtils;

	@RequestMapping("/list.do")
	public void list(Model model, Article article, Page page) {
		saveSessionParams();
		Paged<Article> paged = articleService.findAll(article, page);
		if (paged.getContent() != null && !paged.getContent().isEmpty()) {
			for (Article a : paged.getContent()) {
				a.setModule(moduleService.findById(a.getModuleId()));
			}
		}
		model.addAttribute("paged", paged);
		List<Module> modules = moduleService.findAll("-");
		model.addAttribute("modules", modules);
		model.addAttribute("article", article);
	}

	@GetMapping("/edit.do")
	public void edit(Model model, Article article) {
		if (StringUtils.isNotEmpty(article.getId())) {
			article = articleService.findById(article.getId());
		} else {
			article.setIsPublish(true);
			article.setIsTop(false);
		}
		List<Module> modules = moduleService.findAll("-");
		model.addAttribute("modules", modules);
		model.addAttribute("article", article);
	}

	@PostMapping("/save.do")
	public String save(Model model, Article article, MultipartFile image) {
		try {
			if (image != null && !image.isEmpty()) {
				article.setImageUrl(imageUploadUtils.save((FileInputStream) image.getInputStream(), image.getOriginalFilename(), "article", true));
			}
			article.setAdminId(currentAdmin().getId());
			articleService.save(article);
			model.addAllAttributes(getSessionParams());
			return "redirect:list.do";
		} catch (MessageException e) {
			model.addAttribute(Constants.ERROR_MESSAGE, e.getMessage());
		} catch (Exception e) {
			model.addAttribute(Constants.ERROR_MESSAGE, "保存失败");
		}
		List<Module> modules = moduleService.findAll("-");
		model.addAttribute("modules", modules);
		model.addAttribute("article", article);
		return "/admin/article/edit";
	}

	@PostMapping("/upload.do")
	@ResponseBody
	public Map<String, Object> upload(MultipartFile imgFile) {
		Map<String, Object> map = new HashMap<>();
		try {
			if (imgFile == null || imgFile.isEmpty()) {
				throw new MessageException("图片为空");
			}
			String url = imageUploadUtils.save((FileInputStream) imgFile.getInputStream(), imgFile.getOriginalFilename(), "article", true);
			map.put("error", 0);
			map.put("url", FileUploadUtils.baseUrl + url);
		} catch (MessageException e) {
			map.put("error", 1);
			map.put("message", e.getMessage());
		} catch (Exception e) {
			map.put("error", 1);
			map.put("message", "上传失败");
		}
		return map;
	}
}
