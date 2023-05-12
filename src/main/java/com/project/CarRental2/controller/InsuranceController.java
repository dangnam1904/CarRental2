package com.project.CarRental2.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.CarRental2.model.Insurance;
import com.project.CarRental2.model.User;
import com.project.CarRental2.service.InsuranceService;
import com.project.CarRental2.service.UploadFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class InsuranceController {
	
	@Autowired
	private InsuranceService insuranceService;
	
	@Autowired
	private UploadFile uploadFile;
	
	@GetMapping("/admin/insurance/add")
	public String getFormAdd(Model model,  HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				model.addAttribute("insurance", new Insurance());
				return "admin/pages/insurance/add";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
	}
	
	@GetMapping("/admin/insurance/edit/{id}")
	public String getFormEdit(Model model, @PathVariable("id") int id, 
			 HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				model.addAttribute("insurance", insuranceService.getInsuranceById(id));
				return "admin/pages/insurance/edit";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
	}
	
	@PostMapping("/admin/insurance/edit/{id}")
	public String saveInsuranceEdit(Model model, @ModelAttribute("insurance") Insurance insurance,
			@RequestParam(name ="image", required = false) MultipartFile img,
			 HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				Insurance oldInsurance= insuranceService.getInsuranceById(insurance.getIdInsurance());
				if(img.getOriginalFilename()=="") {
					insurance.setImageInsurance(oldInsurance.getImageInsurance());
				}else
				{
					insurance.setImageInsurance(uploadFile.uploadSingleFile(img));
					uploadFile.removeFile(oldInsurance.getImageInsurance());
				}
				
				insuranceService.saveInsuance(insurance);
				return "redirect:/admin/insurance";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}
	
	@PostMapping("/admin/insurance/add")
	public String saveInsurance(Model model, @ModelAttribute("insurance") Insurance insurance,
			@RequestParam(name ="image", required = false) MultipartFile img,
			 HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				insurance.setCreateDate(new Date());
				insurance.setUpdateDate(new Date());
				insurance.setImageInsurance(uploadFile.uploadSingleFile(img));
				insuranceService.saveInsuance(insurance);
				return "redirect:/admin/insurance";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
	}
	
	@GetMapping("/admin/insurance")
	public String showAll(Model model,  HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				model.addAttribute("list", insuranceService.getAllInsurance());
				return "admin/pages/insurance/list";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
	}

}
