package com.project.CarRental2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.CarRental2.service.BlogService;
import com.project.CarRental2.service.BookingService;
import com.project.CarRental2.service.CarService;
import com.project.CarRental2.service.UserService;

@Controller
public class HomeAdminController {

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CarService carService;
	
	
	@GetMapping("/admin")
	public String getHome(Model model) {
		System.err.println(" tông: "+bookingService.countBill());
		model.addAttribute("countBill", bookingService.countBill());
		model.addAttribute("countCar", carService.countCar());
		model.addAttribute("countUser", userService.countUser());
		model.addAttribute("countBlog", blogService.countBlog());
		return "admin/pages/index";
	}
}
