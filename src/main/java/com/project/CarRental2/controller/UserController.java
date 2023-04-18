package com.project.CarRental2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.CarRental2.model.District;
import com.project.CarRental2.model.Province;
import com.project.CarRental2.model.User;
import com.project.CarRental2.model.Ward;
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
	public String getAllUsuer(Model model) {
		model.addAttribute("listUser", userService.getAllUser());
		return "admin/pages/user/list";
	}

	@PostMapping("/upload-avatar")
	public String uploadAvatar(HttpServletRequest request, @RequestParam(name = "avatar-user") MultipartFile image) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		uploadFile.removeFile(user.getImage());
		user.setImage(uploadFile.uploadSingleFile(image));
		userService.saveUser(user);
		return "redirect:/edit-profile";
	}

	@PostMapping("/update-info-user")
	public String uploadInfoUser(HttpServletRequest request, @RequestParam(name = "name-display") String nameDisplay,
			@RequestParam(name = "dateOfBrith") String dateOfBrith, @RequestParam(name = "sex") int sex,
			@RequestParam("province") Province province, @RequestParam("district") District district,
			@RequestParam("ward") Ward ward, @RequestParam("address-detail") String address) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
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
	}

	@PostMapping("/updatePhone")
	public String updatePhone(HttpServletRequest request, @RequestParam("phone") String phone) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		user.setPhone(phone);
		userService.saveUser(user);
		return "redirect:/edit-profile";
	}
	
	@PostMapping("/upload-gplx")
	public String updateDrivingLicense(HttpServletRequest request, @RequestParam("gplx") String drivingLicense, @RequestParam(name = "image-gplx") MultipartFile image) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		user.setDrivingLicense(drivingLicense);
		uploadFile.removeFile(user.getImgDrivingLicense());
		user.setImgDrivingLicense(uploadFile.uploadSingleFile(image));
		userService.saveUser(user);
		return "redirect:/edit-profile";
	}
	
	@PostMapping("/updateEmail")
	public String updateEmail(HttpServletRequest request, @RequestParam("email") String email) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		user.setEmail(email);
		userService.saveUser(user);
		return "redirect:/edit-profile";
	}

}
