package com.project.CarRental2.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.CarRental2.constants.FiledName;
import com.project.CarRental2.model.RequestWithdrawal;
import com.project.CarRental2.model.User;
import com.project.CarRental2.service.DetailNotificationService;
import com.project.CarRental2.service.NotificationService;
import com.project.CarRental2.service.RequestWithdrawalService;
import com.project.CarRental2.model.DetailNotification;
import com.project.CarRental2.model.Notification;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class RequestPaymentController implements FiledName {

	@Autowired
	private RequestWithdrawalService requestWithdrawalService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired 
	private DetailNotificationService detailNotificationService;
	
	@GetMapping("/admin/request-withdraw")
	public String getAllPayment(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getIdRole() == ROLE_CUSTOMMER_CARE ||  
					sessionUser.getRole().getIdRole() == ROLE_ACCOUNTANT
					|| sessionUser.getRole().getIdRole() == ROLE_ADMIN ) {
				model.addAttribute("list", requestWithdrawalService.getAllRequestWithdraw());
				return "admin/pages/payment/list";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
	}
	
	@GetMapping("/admin/request-withdraw/status-approve/{idRequest}")
	public String ApprovePayment(Model model, HttpServletRequest request,
			@PathVariable("idRequest") int idRequest) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getIdRole() == ROLE_CUSTOMMER_CARE ||  
					sessionUser.getRole().getIdRole() == ROLE_ACCOUNTANT
					|| sessionUser.getRole().getIdRole() == ROLE_ADMIN ) {
				requestWithdrawalService.changeStatusRequestWithdraw(STATUS_APPROVED, idRequest);
				RequestWithdrawal requestWithdrawal= requestWithdrawalService.getRequestWithdrawalById(idRequest);
				System.err.println(requestWithdrawal.toString());
				DetailNotification detailNotification = new DetailNotification(0, NO_READING, "/my-walet",
						requestWithdrawal.getUser(), new Notification(0, "/logo1.png", "Yêu cầu rút tiền đã được duyệt",
								"Yêu cầu rút tiền của bạn đã được phê duyệt", new Date(), new Date()));
				notificationService.saveNotification(detailNotification.getNotification());
				detailNotificationService.saveDetail(detailNotification);
				return "redirect:/admin/request-withdraw";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
	}
	
	@GetMapping("/admin/request-withdraw/status-cancel/{idRequest}")
	public String CancelPayment(Model model, HttpServletRequest request,
			@PathVariable("idRequest") int idRequest) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getIdRole() == ROLE_CUSTOMMER_CARE ||  
					sessionUser.getRole().getIdRole() == ROLE_ACCOUNTANT
					|| sessionUser.getRole().getIdRole() == ROLE_ADMIN ) {
				requestWithdrawalService.changeStatusRequestWithdraw(STATUS_CANCAL, idRequest);
				RequestWithdrawal requestWithdrawal= requestWithdrawalService.getRequestWithdrawalById(idRequest);
				System.err.println(requestWithdrawal.toString());
				DetailNotification detailNotification = new DetailNotification(0, NO_READING, "/my-walet",
						requestWithdrawal.getUser(), new Notification(0, "/logo1.png", "Yêu cầu rút tiền không được duyệt",
								"Yêu cầu rút tiền của bạn không được duyệt", new Date(), new Date()));
				notificationService.saveNotification(detailNotification.getNotification());
				detailNotificationService.saveDetail(detailNotification);
				return "redirect:/admin/request-withdraw";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
	}
}
