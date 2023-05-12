package com.project.CarRental2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.CarRental2.model.User;
import com.project.CarRental2.service.RequestWithdrawalService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class RequestPaymentController {

	@Autowired
	private RequestWithdrawalService requestWithdrawalService;
	
	@GetMapping("/admin/request-payment")
	public String getAllPayment(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				model.addAttribute("list", requestWithdrawalService.getAllRequestWithdraw());
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
