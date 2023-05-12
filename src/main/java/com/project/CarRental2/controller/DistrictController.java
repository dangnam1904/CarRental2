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

import com.project.CarRental2.model.District;
import com.project.CarRental2.model.User;
import com.project.CarRental2.service.DistrictService;
import com.project.CarRental2.service.ProvinceService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class DistrictController {

	@Autowired
	private DistrictService districtService;
	@Autowired 
	private ProvinceService provinceService;
	@GetMapping("/district")
	public String getHome(Model model,  HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				model.addAttribute("listDistrict", districtService.getAllDistricWithProvenice());
				return "admin/pages/district/list";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}
	@GetMapping("/district/add")
	public String formAdd(Model model,  HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {

				model.addAttribute("province",provinceService.getAllProvinceOrderByName());
				model.addAttribute("district", new District());
				return "admin/pages/district/add";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}
	
	@PostMapping("/district/add")
	public String AddDistrict(@ModelAttribute("district") District district, RedirectAttributes rAttributes,
			 HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				district.setCreateDate(new Date());
				district.setUpdateDate(new Date());
				List<District> dList= districtService.getAllDistrict();
				if(dList.size()<=0) {
					districtService.saveDistrict(district);
					return "redirect:/admin/district";
				}
				for (District district2 : dList) {
					if(district.getProvince().getIdProvince()== district2.getProvince().getIdProvince() 
							&& district.getNameDistrict().equalsIgnoreCase(district2.getNameDistrict())) {
						rAttributes.addFlashAttribute("mes", "Quận /Huyện này đã có "); 
						return "redirect:/admin/district/add";
					}
				}
				districtService.saveDistrict(district);

				return "redirect:/admin/district";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}
	@GetMapping("/district/edit/{id}")
	public String GetForm(Model model, @PathVariable(name = "id") int id,
			 HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				model.addAttribute("district", districtService.getDistrict(id));
				model.addAttribute("province",provinceService.getAllProvinceOrderByName());
				return "admin/pages/district/edit";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}
	
	@PostMapping("/district/edit")
	public String EditDistrict(@ModelAttribute("district") District district, RedirectAttributes rAttributes,
			 HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				String url="redirect:/admin/district";
				// save old date create
				District oldDistrict= districtService.getDistrict(district.getIdDistrict());
				district.setCreateDate(oldDistrict.getCreateDate());
				district.setUpdateDate(new Date());
				// equals data
				List<District> dList= districtService.getAllDistrict();
				if(dList.size()<=0) {
					districtService.saveDistrict(district);
					url="redirect:/admin/district";
				}
				for (District district2 : dList) {
					if(district.getProvince().getIdProvince()== district2.getProvince().getIdProvince() 
							&& district.getNameDistrict().equalsIgnoreCase(district2.getNameDistrict())) {
						rAttributes.addFlashAttribute("mes", "Quận /Huyện này đã có "); 
						return "redirect:/admin/district/edit/"+oldDistrict.getIdDistrict();
					}
				}
				districtService.saveDistrict(district);
				return url;
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	
		
	}
	
	@GetMapping("/district/delete/{id}")
	public String deleteDistrict(@PathVariable(name="id") int id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				districtService.deleteDistrict(id);
				return "redirect:/admin/district";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	
	}
	
	
}
