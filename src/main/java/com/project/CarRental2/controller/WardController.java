package com.project.CarRental2.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.CarRental2.model.User;
import com.project.CarRental2.model.Ward;
import com.project.CarRental2.service.DistrictService;
import com.project.CarRental2.service.ProvinceService;
import com.project.CarRental2.service.WardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class WardController {

	@Autowired
	private WardService wardService;
	@Autowired
	private DistrictService districtService;
	@Autowired
	private ProvinceService  provinceService;
	
	@GetMapping("/admin/ward")
	public String getHomeWard( Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				model.addAttribute("listward", wardService.getAllWardWithDistrictWithProvinces());		
				return "admin/pages/ward/list";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}
	
	@GetMapping("/admin/ward/add")
	public String getFormAdd(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				model.addAttribute("province",provinceService.getAllProvinceOrderByName());
				model.addAttribute("ward", new Ward());
				return "admin/pages/ward/add";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
	}
	
	@GetMapping("/admin/ward/edit/{id}")
	public String getFormAdd(Model model,@PathVariable(name="id") int id,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				model.addAttribute("province",provinceService.getAllProvinceOrderByName());
				System.out.println(wardService.getAWard(id));
				model.addAttribute("ward",wardService.getAWard(id));
				model.addAttribute("district",
						districtService.getAllDistrictByIdProvince(
								wardService.getAWard(id).getDistrict().getProvince().getIdProvince()));
				
				return "admin/pages/ward/edit";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
		
	}
	
	@PostMapping("/admin/ward/edit")
	public String editWard(@ModelAttribute("ward") Ward w, Model model, 
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				Ward old_ward= wardService.getAWard(w.getIdWard());
				w.setCreateDate(old_ward.getCreateDate());
				w.setUpdateDate(new Date());
				wardService.saveWard(w);
				return "redirect:/admin/ward";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}
	
	@PostMapping("/admin/ward/add")
	public String addWard(@ModelAttribute("ward") Ward w, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				System.err.println(w.toString());
				System.err.println (w.getDistrict().getProvince().getIdProvince());
				w.setCreateDate(new Date());
				w.setUpdateDate(new Date());
				wardService.saveWard(w);
				return "redirect:/admin/ward";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}
}
