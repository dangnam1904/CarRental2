package com.project.CarRental2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

	@GetMapping("/")
	public String hy() {
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

	@GetMapping("/header")
	public String he() {
		return "pages/layout/header";
	}

	@GetMapping("/reggister-car")
	public String registerCar() {
		return "pages/reigster-car";
	}
}
