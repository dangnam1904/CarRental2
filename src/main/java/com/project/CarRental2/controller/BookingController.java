package com.project.CarRental2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.CarRental2.constants.FiledName;
import com.project.CarRental2.model.Booking;
import com.project.CarRental2.model.User;
import com.project.CarRental2.service.BookingService;
import com.project.CarRental2.service.UserService;



@Controller
@RequestMapping("/admin/booking")
public class BookingController  implements FiledName{

	@Autowired 
	private BookingService bookingService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("")
	public String getAllBooking(Model model) {
		model.addAttribute("listBooking", bookingService.getAllBooking());
		return "admin/pages/booking/list";
	}
	@GetMapping("/approved-bill/{id}")
	public String changStatusApprovedBill(@PathVariable(name = "id") int id) {
		try {
			bookingService.changeStatusBill(STATUS_APPROVED, id);
		} catch (JpaSystemException e) {
			System.err.println("ex");
		}

		return "redirect:/admin/booking";
	}

	@GetMapping("/cancel-bill/{id}")
	public String changStatusCancelBill(@PathVariable(name = "id") int id) {
		try {
			bookingService.changeStatusBill(STATUS_CANCAL, id);
		} catch (JpaSystemException e) {
			System.err.println("ex");
		}

		return "redirect:/admin/booking";
	}
	
	@GetMapping("/cancel-bill-trip/{id}")
	public String changStatusCancelBillUser(@PathVariable(name = "id") int id) {
		try {
			bookingService.changeStatusBill(STATUS_CANCAL, id);
		} catch (JpaSystemException e) {
			System.err.println("ex");
		}

		return "redirect:/admin/booking";
	}

	@GetMapping("/restore-bill/{id}")
	public String changStatusRestoreBill(@PathVariable(name = "id") int id) {
		try {
			bookingService.changeStatusBill(STATUS_PENDING, id);
		} catch (JpaSystemException e) {
			e.printStackTrace();
		}

		return "redirect:/admin/booking";
	}

	@GetMapping("/payment-bill/{id}")
	public String changStatusPaymentBill(@PathVariable(name = "id") int id) {
		try {
			Booking booking = bookingService.getABooking(id);
			User u = userService.getAUser(booking.getCar().getUser().getIdUser());
			System.err.println(u.toString());
			u.setTotalMoney(u.getTotalMoney() + booking.getBillTotal());
			System.err.println("Tông tiên: "+ u.getTotalMoney());
			try {
				userService.updateTotalMoney(u.getTotalMoney(), u.getIdUser());
			} catch (JpaSystemException e) {
			}
			bookingService.changeStatusBill(STATUS_PAYMENT, id);
			
		} catch (JpaSystemException e) {
			e.printStackTrace();
		}
		return "redirect:/admin/booking";
	}
	@GetMapping("/statusbill/{statusBill}")
	public String getBillByStatusBill(Model model,@PathVariable(name = "statusBill") int statusBill) {
		model.addAttribute("listBooking", bookingService.getAllBookingByStatusBill(statusBill));
		return "redirect:/admin/booking";
	}
	
}
