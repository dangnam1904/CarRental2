package com.project.CarRental2.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.CarRental2.model.Ward;
import com.project.CarRental2.service.DistrictService;
import com.project.CarRental2.service.ProvinceService;
import com.project.CarRental2.service.WardService;

@Controller
public class WardController {

	@Autowired
	private WardService wardService;
	@Autowired
	private DistrictService districtService;
	@Autowired
	private ProvinceService  provinceService;
	
	@GetMapping("/admin/ward")
	public String getHomeWard( Model model) {
		model.addAttribute("listward", wardService.getAllWardWithDistrictWithProvinces());		
		return "admin/pages/ward/list";
	}
	
	@GetMapping("/admin/ward/add")
	public String getFormAdd(Model model )  {
//		model.addAttribute("district", districtService.getAllDistrict());
		model.addAttribute("province",provinceService.getAllProvinceOrderByName());
		model.addAttribute("ward", new Ward());
		return "admin/pages/ward/add";
	}
	
	@GetMapping("/admin/ward/edit/{id}")
	public String getFormAdd(Model model,@PathVariable(name="id") int id )  {
		model.addAttribute("province",provinceService.getAllProvinceOrderByName());
		System.out.println(wardService.getAWard(id));
		model.addAttribute("ward",wardService.getAWard(id));
		model.addAttribute("district",
				districtService.getAllDistrictByIdProvince(wardService.getAWard(id).getDistrict().getProvince().getIdProvince()));
		
		return "admin/pages/ward/edit";
	}
	
	@PostMapping("/admin/ward/edit")
	public String editWard(@ModelAttribute("ward") Ward w, Model model )  {
		Ward old_ward= wardService.getAWard(w.getIdWard());
		w.setCreateDate(old_ward.getCreateDate());
		w.setUpdateDate(new Date());
		wardService.saveWard(w);
		return "redirect:/admin/ward";
	}
	
	@PostMapping("/admin/ward/add")
	public String addWard(@ModelAttribute("ward") Ward w, Model model )  {
		System.err.println(w.toString());
		System.err.println (w.getDistrict().getProvince().getIdProvince());
		w.setCreateDate(new Date());
		w.setUpdateDate(new Date());
		wardService.saveWard(w);
		return "redirect:/admin/ward";
	}
	

}
