package com.project.CarRental2.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.CarRental2.model.BrandCar;
import com.project.CarRental2.service.BrandCarService;

@Controller
public class BrandCarControlller {

	@Autowired
	private BrandCarService brandCarService;

	@GetMapping (path = { "/admin/brand-car", "brand-car"})
	public String getAllBrandCar(Model model) {
		model.addAttribute("brandcar", brandCarService.getAllBrandCarOderByNameAsc());
	
		return "admin/pages/brandcar/list";
	}
	@GetMapping("admin/brand-car/add")
	public String getForm(Model model, RedirectAttributes ra) {
		model.addAttribute("brandcar", new BrandCar());
		model.addAttribute("action", "Thêm");
		return "admin/pages/brandcar/form";
	}
	
	@GetMapping("admin/brand-car/edit/{id}")
	public String editBrandCar(Model model, RedirectAttributes ra, @PathVariable(name="id") int id) {
		model.addAttribute("brandcar", brandCarService.getBrandCarById(id));
		model.addAttribute("action", "Sửa");
		return "admin/pages/brandcar/form";
	}
	
	@GetMapping("admin/brand-car/delete/{id}")
	public String deleteBrandCar( @PathVariable(name="id") int id) {
		brandCarService.deleteBrandCar(id);
		return "redirect:/admin/brand-car";
	}
	
	@PostMapping("admin/brand-car/save")
	public String saveBrandCar(@ModelAttribute("brandcar") BrandCar brandCar, RedirectAttributes ra) {
		System.out.println(brandCar.toString());
		BrandCar oldBrandCar= brandCarService.getBrandCarById(brandCar.getIdBrand());
		
		if(oldBrandCar!=null) {
		
			brandCar.setCreateDate(oldBrandCar.getCreateDate());
			
		}else {
			brandCar.setCreateDate(new Date());
			
		}
		brandCar.setUpdateDate(new Date());
		if(brandCarService.checkNameBranchExist(brandCarService.getAllBrachCar(), brandCar)) {
			ra.addFlashAttribute("mes","Đã tồn tại tên này");
			
		}else {
			brandCarService.saveBrandCar(brandCar);
		}
		
		
		return "redirect:/admin/brand-car";
	}
}
