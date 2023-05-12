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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.CarRental2.model.Province;
import com.project.CarRental2.model.User;
import com.project.CarRental2.service.ProvinceService;
import com.project.CarRental2.service.UploadFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class ProvinceController {

	@Autowired
	private ProvinceService provinceService;
	@Autowired 
	private UploadFile uploadFile;

	@GetMapping("/provinces")
	public String getAllProvinceOrderByName(Model model,  HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {

				List<Province> list = provinceService.getAllProvinceOrderByName();

				model.addAttribute("listprovince", list);
				return "admin/pages/provinces/list";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}

	@GetMapping("/provinces/add")
	public String getForm(Model model,  HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				model.addAttribute("province", new Province());
				return "admin/pages/provinces/add";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
	}

	@PostMapping("/provinces/add")
	public String AddProvince(@ModelAttribute("province") Province province,
			@RequestParam(name="image", required = false) MultipartFile img,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
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
							
							redirectAttributes.addFlashAttribute("mes", "Tỉnh này đã tồn tại");
							return "redirect:/admin/provinces/add";
						}
					}
					province.setImgProvince(uploadFile.uploadSingleFile(img));
					provinceService.saveProvices(province);
					url = "redirect:/admin/provinces";
				}

				return url;
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
	}

	@GetMapping("/provinces/edit/{id}")
	public String Edit(Model model, @PathVariable(name = "id") int id,
			 HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				model.addAttribute("province", provinceService.getProvince(id));
				return "admin/pages/provinces/edit";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
	}

	@PostMapping("/provinces/edit")
	public String EditProvince(@ModelAttribute("province") Province province,
			@RequestParam(name="image", required = false) MultipartFile img,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				Province oldProvince = provinceService.getProvince(province.getIdProvince());
				province.setUpdateDate(new Date());
				province.setCreateDate(oldProvince.getCreateDate());
				int id = province.getIdProvince();
			
				String url = "";
				List<Province> list = provinceService.getAllProvinceOrderByName();
				for (Province province2 : list) {
					if (province.getNameProvince().equals(province2.getNameProvince())) {
						redirectAttributes.addFlashAttribute("mes", "Tỉnh này đã tồn tại");
						return "redirect:/admin/provinces/edit/" + id;
					}
				}
				
				if(img.getOriginalFilename()=="") {
					province.setImgProvince(oldProvince.getImgProvince());
				}else {
					province.setImgProvince(uploadFile.uploadSingleFile(img));
					uploadFile.removeFile(oldProvince.getImgProvince());
				}
				
				provinceService.saveProvices(province);
				url = "redirect:/admin/provinces";
				return url;
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
	}

	@GetMapping("/provinces/delete/{id}")
	public String deleteProvinces(@PathVariable(name = "id") int id,
			 HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				provinceService.deleteProvince(id);
				return "redirect:/admin/provinces";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}
}
