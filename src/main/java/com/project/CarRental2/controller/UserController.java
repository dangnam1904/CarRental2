package com.project.CarRental2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.CarRental2.model.District;
import com.project.CarRental2.model.Province;
import com.project.CarRental2.model.User;
import com.project.CarRental2.model.Ward;
import com.project.CarRental2.service.EncryptionPassword;
import com.project.CarRental2.service.UploadFile;
import com.project.CarRental2.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	UploadFile uploadFile;

	@GetMapping("/admin/user")
	public String getAllUsuer(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if (sessionUser !=null && sessionUser.getRole().getNameRole().equals("Admin")) {
			model.addAttribute("listUser", userService.getAllUser());
			return "admin/pages/user/list";
			
		} else {
			return "redirect:/login";
		}
	}

	@PostMapping("/upload-avatar")
	public String uploadAvatar(HttpServletRequest request,
			@RequestParam(name = "avatar-user", required = false) MultipartFile image) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("sesionUser");
		if(user!= null) {
			uploadFile.removeFile(user.getImage());
			user.setImage(uploadFile.uploadSingleFile(image));
			userService.saveUser(user);
			return "redirect:/edit-profile";
		}else {
			return "redirect:/login";
		}
	}

	@PostMapping("/update-info-user")
	public String uploadInfoUser(
			HttpServletRequest request, @RequestParam(name = "name-display") String nameDisplay,
			@RequestParam(name = "dateOfBrith") String dateOfBrith, @RequestParam(name = "sex") int sex,
			@RequestParam("province") Province province, @RequestParam("district") District district,
			@RequestParam("ward") Ward ward, @RequestParam("address-detail") String address) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("sesionUser");
		if(user !=null) {
			user.setNameDisplay(nameDisplay);
			user.setDateOfBrith(dateOfBrith);
			if (sex == 1) {
				user.setSex(false);
			} else {
				user.setSex(true);
			}
			user.setAddress(address + "," + ward.getNameWard() + "," + district.getNameDistrict() + ","
					+ province.getNameProvince());
			userService.saveUser(user);
			return "redirect:/edit-profile";
		}else {
			return "redirect:/login";
		}
	}

	@PostMapping("/updatePhone")
	public String updatePhone(HttpServletRequest request, @RequestParam("phone") String phone) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("sesionUser");
		if(user != null) {
			user.setPhone(phone);
			userService.saveUser(user);
			return "redirect:/edit-profile";
		}else {
			return "redirect:/login";
		}
	}

	@PostMapping("/upload-gplx")
	public String updateDrivingLicense(HttpServletRequest request, @RequestParam("gplx") String drivingLicense,
			@RequestParam(name = "image-gplx", required = false) MultipartFile image) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("sesionUser");
		if(user != null) {
			user.setDrivingLicense(drivingLicense);
			if (image.getOriginalFilename() != "") {
				uploadFile.removeFile(user.getImgDrivingLicense());
				user.setImgDrivingLicense(uploadFile.uploadSingleFile(image));
			}
			userService.saveUser(user);
			return "redirect:/edit-profile";
		}else {
			return "redirect:/login";
		}
		
	}

	@PostMapping("/updateEmail")
	public String updateEmail(HttpServletRequest request, @RequestParam("email") String email) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("sesionUser");
		if(user != null) {
			user.setEmail(email);
			userService.saveUser(user);
			return "redirect:/edit-profile";
		}else {
			return "redirect:/login";
		}
	}

	@GetMapping("/admin/user/resetPass/{idUser}")
	public String resetPassword(Model model, @PathVariable("idUser") int idUser, HttpServletRequest request,
			RedirectAttributes ra) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				User user = userService.getAUser(idUser);
				user.setPassword(EncryptionPassword.encryption("123"));
				userService.saveUser(user);
				ra.addFlashAttribute("messResetPass", "Success");
				
				return "redirect:/admin/user";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}

	@GetMapping("admin/user/delete/{idUser}")
	public String deleteUser(Model model, @PathVariable("idUser") int idUser, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				userService.deleteUser(idUser);
				return "redirect:/admin/user";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}
}
