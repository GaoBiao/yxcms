package org.hebgb.app.cms.controller.admin;

import java.io.FileInputStream;

import org.apache.commons.lang3.StringUtils;
import org.hebgb.app.cms.commons.AjaxResult;
import org.hebgb.app.cms.commons.Constants;
import org.hebgb.app.cms.commons.exception.MessageException;
import org.hebgb.app.cms.model.IndexSlider;
import org.hebgb.app.cms.service.IIndexSliderService;
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

@Controller("admin_IndexSliderController")
@RequestMapping("/admin/indexSlider")
public class IndexSliderController extends AbstractAdminController {
	@Autowired
	private IIndexSliderService indexSliderService;
	@Autowired
	private FileUploadUtils imageUploadUtils;

	@RequestMapping("/list.do")
	public void list(Model model, IndexSlider indexSlider, Page page) {
		saveSessionParams();
		Paged<IndexSlider> paged = indexSliderService.findAll(indexSlider, page);
		model.addAttribute("paged", paged);
	}

	@GetMapping("/edit.do")
	public void edit(Model model, IndexSlider indexSlider) {
		if (StringUtils.isNotEmpty(indexSlider.getId())) {
			indexSlider = indexSliderService.findById(indexSlider.getId());
		} else {
			indexSlider.setIsShow(true);
		}
		model.addAttribute("indexSlider", indexSlider);
	}

	@PostMapping("/save.do")
	public String save(Model model, IndexSlider indexSlider, MultipartFile image) {
		try {
			if (image != null && !image.isEmpty()) {
				indexSlider.setImageUrl(imageUploadUtils.save((FileInputStream) image.getInputStream(), image.getOriginalFilename(), "indexSlider", true));
			}
			indexSliderService.save(indexSlider);
			model.addAllAttributes(getSessionParams());
			return "redirect:list.do";
		} catch (MessageException e) {
			model.addAttribute(Constants.ERROR_MESSAGE, e.getMessage());
		} catch (Exception e) {
			model.addAttribute(Constants.ERROR_MESSAGE, "保存失败");
		}
		model.addAttribute("indexSlider", indexSlider);
		return "/admin/indexSlider/edit";
	}

	@RequestMapping("/delete.do")
	@ResponseBody
	public AjaxResult delete(String id) {
		AjaxResult result = new AjaxResult();
		indexSliderService.deleteById(id);
		return result;
	}

	@RequestMapping("/move.do")
	@ResponseBody
	public AjaxResult move(String id, String position) {
		AjaxResult result = new AjaxResult();
		if ("up".equalsIgnoreCase(position)) {
			indexSliderService.moveUp(id);
		} else {
			indexSliderService.moveDown(id);
		}
		return result;
	}
}
