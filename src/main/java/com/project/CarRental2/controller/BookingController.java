package com.project.CarRental2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.CarRental2.constants.FiledName;
import com.project.CarRental2.model.Booking;
import com.project.CarRental2.model.User;
import com.project.CarRental2.service.BookingService;
import com.project.CarRental2.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller

public class BookingController implements FiledName {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private UserService userService;

	@GetMapping("/admin/booking")
	public String getAllBooking(Model model) {
		model.addAttribute("listBooking", bookingService.getAllBooking());
		return "admin/pages/booking/list";
	}

	@GetMapping("/admin/booking/approved-bill/{id}")
	public String changStatusApprovedBill(@PathVariable(name = "id") int id) {
		try {
			bookingService.changeStatusBill(STATUS_APPROVED, id);
		} catch (JpaSystemException e) {
			System.err.println("ex");
		}

		return "redirect:/admin/booking";
	}

	@GetMapping("/admin/booking/cancel-bill/{id}")
	public String changStatusCancelBill(@PathVariable(name = "id") int id) {
		try {
			bookingService.changeStatusBill(STATUS_CANCAL, id);
		} catch (JpaSystemException e) {
			System.err.println("ex");
		}

		return "redirect:/admin/booking";
	}

	@GetMapping("/admin/booking/cancel-bill-trip/{id}")
	public String changStatusCancelBillUser(@PathVariable(name = "id") int id) {
		try {
			bookingService.changeStatusBill(STATUS_CANCAL, id);
		} catch (JpaSystemException e) {
			System.err.println("ex");
		}

		return "redirect:/admin/booking";
	}

	@GetMapping("/admin/booking/restore-bill/{id}")
	public String changStatusRestoreBill(@PathVariable(name = "id") int id) {
		try {
			bookingService.changeStatusBill(STATUS_PENDING, id);
		} catch (JpaSystemException e) {
			e.printStackTrace();
		}

		return "redirect:/admin/booking";
	}

	@GetMapping("/admin/booking/payment-bill/{id}")
	public String changStatusPaymentBill(@PathVariable(name = "id") int id) {
		try {
			Booking booking = bookingService.getABooking(id);
			User u = userService.getAUser(booking.getCar().getUser().getIdUser());
			System.err.println(u.toString());
			u.setTotalMoney(u.getTotalMoney() + booking.getBillTotal());
			System.err.println("Tông tiên: " + u.getTotalMoney());
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

	@GetMapping("/admin/booking/statusbill/{statusBill}")
	public String getBillByStatusBill(Model model, @PathVariable(name = "statusBill") int statusBill) {
		model.addAttribute("listBooking", bookingService.getAllBookingByStatusBill(statusBill));
		return "redirect:/admin/booking";
	}

	@GetMapping("/get-bill-status/{status-bill}")
	public String filterBillByStatus(Model model, HttpServletRequest request,
			@PathVariable(name = "status-bill") int statusBill) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/";
		} else {
			model.addAttribute("user", user);
			if (statusBill == ALL_STATUS) {
				model.addAttribute("listBooking", 
						bookingService.getAllBookingWithCarOwner(user.getIdUser()));
			} else {
				model.addAttribute("listBooking",
						bookingService.getAllBookingWithCarOwnerAndStatusBill(user.getIdUser(), statusBill));
			}
			return "pages/my-bill";
		}
	}
	
	@GetMapping("/admin/booking/get-bill-status/{status-bill}")
	public String filterBillByStatusAdmin(Model model, HttpServletRequest request,
			@PathVariable(name = "status-bill") int statusBill) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		} else {
			model.addAttribute("user", user);
			if (statusBill == ALL_STATUS) {
				model.addAttribute("listBooking", bookingService.getAllBooking());
			} else {
				model.addAttribute("listBooking", bookingService.getAllBookingByStatusBill(statusBill));
			}
			return "admin/pages/booking/list";
		}
	}
}
