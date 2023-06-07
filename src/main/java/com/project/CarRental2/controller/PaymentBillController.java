package com.project.CarRental2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.CarRental2.constants.FiledName;
import com.project.CarRental2.model.User;
import com.project.CarRental2.service.PaymentBillService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentBillController implements FiledName {
	
	@Autowired
	private PaymentBillService paymentBillService;

	@GetMapping("/admin/payment")
	public String CancelPayment(Model model, HttpServletRequest request
			) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getIdRole() == ROLE_CUSTOMMER_CARE ||  
					sessionUser.getRole().getIdRole() == ROLE_ACCOUNTANT
					|| sessionUser.getRole().getIdRole() == ROLE_ADMIN ){
				model.addAttribute("list", paymentBillService.getAllPaymentBill());
				System.err.println(paymentBillService.getAllPaymentBill());
				return "/admin/pages/payment/history-payment-bill";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
	}
}
