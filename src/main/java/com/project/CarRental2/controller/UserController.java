package com.project.CarRental2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.CarRental2.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/admin/user")
	public String getAllUsuer(Model model)
	{
		model.addAttribute("listUser", userService.getAllUser());
		return "admin/pages/user/list";
	}

}
