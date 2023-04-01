package com.project.CarRental2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeAdminController {

	@GetMapping("/admin")
	public String getHome() {
		return "admin/pages/index";
	}
}
