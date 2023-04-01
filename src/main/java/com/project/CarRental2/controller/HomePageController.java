package com.project.CarRental2.controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.CarRental2.model.Role;
import com.project.CarRental2.model.User;
import com.project.CarRental2.service.EncryptionPassword;
import com.project.CarRental2.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomePageController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String HomePage() {
		return "index";
	}

	@GetMapping("/car-address")
	public String carAddress() {
		return "pages/address-car";
	}

	@GetMapping("/car-detail")
	public String carDetail() {
		return "pages/car-detail";
	}

	@GetMapping("/filter")
	public String filterCar() {
		return "pages/filter-car";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "pages/login";
	}

	@PostMapping("/login")
	public String checkLogin(@ModelAttribute("user") User user, HttpServletRequest request,
			RedirectAttributes rAttributes) {
		String url = "";
		HttpSession sessionUser = request.getSession();
		user.setPassword(EncryptionPassword.encryption(user.getPassword()));
		List<User> list = userService.getAllUserOrderByUsername();
		for (User user2 : list) {
			if (user.getUsername().equals(user2.getUsername()) && user.getPassword().equals(user2.getPassword())) {
				sessionUser.setAttribute("user", user2);
				url = "redirect:/";
				break;
			} else {
				rAttributes.addFlashAttribute("mes", "Tài khoản không chính xác");
				url = "redirect:/login";
			}
		}
		return url;
	}

	@GetMapping("/sign-up")
	public String registerUser(Model model) {
		model.addAttribute("user", new User());
		return "pages/resgiter";
	}

	@PostMapping("/sign-up")
	public String register_User(@ModelAttribute("user") User user, RedirectAttributes rAttributes) throws SQLException {
		user.setPassword(EncryptionPassword.encryption(user.getPassword()));
		String url = "";
		System.out.println(user.getPassword());
		user.setCreateDate(new Date());
		user.setUpdateDate(new Date());
		user.setRole(new Role(2));
		user.getRole().getIdRole();
		System.out.println(user);
		List<User> list = userService.getAllUserOrderByUsername();
		System.out.println(list.toString());
		for (int i = 0; i < list.size(); i++) {
			if (user.getUsername().equals(list.get(i).getUsername())) {
				rAttributes.addFlashAttribute("mes", "Taì khoản đã tồn tại");
				url = "redirect:/sign-up";
				break;

			} else {
				userService.saveUser(user);
				rAttributes.addFlashAttribute("mes", "Đăng kí thành công");
				url = "redirect:/login";
			}
		}
		return url;
	}

	@GetMapping("/header")
	public String he() {
		return "pages/layout/header";
	}

	@GetMapping("/register-car")
	public String registerCar() {
		return "pages/reigster-car";
	}
}
