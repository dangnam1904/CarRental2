package com.project.CarRental2.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.CarRental2.model.Province;
import com.project.CarRental2.service.ProvinceService;

@Controller
@RequestMapping("/admin")
public class ProvinceController {

	@Autowired
	private ProvinceService provinceService;

	@GetMapping("/provinces")
	public String getAllProvinceOrderByName(Model model) {
		List<Province> list = provinceService.getAllProvinceOrderByName();

		model.addAttribute("listprovince", list);
		return "admin/pages/provinces/list";
	}

	@GetMapping("/provinces/add")
	public String getForm(Model model) {
		model.addAttribute("province", new Province());
		return "admin/pages/provinces/add";
	}

	@PostMapping("/provinces/add")
	public String AddProvince(@ModelAttribute("province") Province province, RedirectAttributes redirectAttributes) {
		province.setCreateDate(new Date());
		province.setUpdateDate(new Date());
		String url = "";
		List<Province> list = provinceService.getAllProvinceOrderByName();
		if (list.size() <= 0) {
			provinceService.saveProvices(province);
			url = "redirect:/admin/provinces";
		} else {
			for (Province province2 : list) {
				if (province.getNameProvince().equals(province2.getNameProvince())) {
					url = "redirect:/admin/provinces/add";
					redirectAttributes.addFlashAttribute("mes", "Tỉnh này đã tồn tại");
					break;
				} else {
					provinceService.saveProvices(province);
					url = "redirect:/admin/provinces";
				}
			}
		}

		return url;
	}

	@GetMapping("/provinces/edit/{id}")
	public String Edit(Model model, @PathVariable(name = "id") int id) {
		model.addAttribute("province", provinceService.getProvince(id));
		return "admin/pages/provinces/edit";
	}

	@PostMapping("/provinces/edit")
	public String EditProvince(@ModelAttribute("province") Province province, RedirectAttributes redirectAttributes) {

		Province oldProvince = provinceService.getProvince(province.getIdProvince());
		province.setUpdateDate(new Date());
		province.setCreateDate(oldProvince.getCreateDate());
		int id = province.getIdProvince();
		System.out.println(province.toString());
		String url = "";
		List<Province> list = provinceService.getAllProvinceOrderByName();
		for (Province province2 : list) {
			if (province.getNameProvince().equals(province2.getNameProvince())) {
				url = "redirect:/admin/provinces/edit/" + id;
				redirectAttributes.addFlashAttribute("mes", "Tỉnh này đã tồn tại");
				break;
			} else {
				provinceService.saveProvices(province);
				url = "redirect:/admin/provinces";
			}
		}
		return url;
	}

	@GetMapping("/provinces/delete/{id}")
	public String deleteProvinces(@PathVariable(name = "id") int id) {

		provinceService.deleteProvince(id);
		return "redirect:/admin/provinces";
	}
}