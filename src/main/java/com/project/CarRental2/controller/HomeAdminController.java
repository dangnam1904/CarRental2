package com.project.CarRental2.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.CarRental2.constants.FiledName;
import com.project.CarRental2.model.Booking;
import com.project.CarRental2.model.Event;
import com.project.CarRental2.model.User;
import com.project.CarRental2.service.BlogService;
import com.project.CarRental2.service.BookingService;
import com.project.CarRental2.service.CarService;
import com.project.CarRental2.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeAdminController implements FiledName {

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CarService carService;
	
	
	@GetMapping("/admin")
	public String getHome(Model model,  HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				System.err.println(" tông: "+bookingService.countBill());
				model.addAttribute("countBill", bookingService.countBill());
				model.addAttribute("countCar", carService.countCar());
				model.addAttribute("countUser", userService.countUser());
				model.addAttribute("countBlog", blogService.countBlog());

				// Lấy ngày hiện tại
				LocalDate now = LocalDate.now();
				LocalDate nowPlus= now.plusDays(1);
				System.out.println(" ngay sau khi cong: "+now.plusDays(1));
				// Lấy ngày cách đây 30 ngày
				LocalDate last30Days = now.minusDays(30);
				String[] array= bookingService.sumRevenueOnTime(last30Days.toString(),nowPlus.toString(),STATUS_PAYMENT);
				String valueNgay = "";
				valueNgay += "[";
				String valueTongTien = "";
				valueTongTien += "[";	
				for( int i=0; i<array.length;i++) {
					System.err.println(array[i]);
					String[] arra= array[i].split(",");
					valueNgay=valueNgay+ "\""+arra[0]+"\""+"," ;
					valueTongTien=valueTongTien+ arra[1]+",";
				}
				valueNgay=valueNgay.substring(0, valueNgay.length()-1);
				valueTongTien=valueTongTien.substring(0,valueTongTien.length()-1);
				valueNgay=valueNgay+"]";
				valueTongTien=valueTongTien+"]";
				model.addAttribute("valueDate", valueNgay);
				model.addAttribute("totalMoney", valueTongTien);
				return "admin/pages/index";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
	}
	@GetMapping("/admin/analysist")
	public String getAnalysis(Model model,  HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				return "admin/pages/analysis/analysis";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
	}
	
	@PostMapping("/admin/analysist")
	public String getAnalysis(Model model, @RequestParam("dateStart") String dateStart,
			@RequestParam("dateEnd") String dateEnd,  HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				List<Booking> listBooking= bookingService.getBookingOnTimeByStatusBill(dateStart, dateEnd, STATUS_PAYMENT);
				model.addAttribute("listBooking", listBooking);
				String[] array= bookingService.sumRevenueOnTime(dateStart,dateEnd,STATUS_PAYMENT);
				String valueNgay = "";
				valueNgay += "[";
				String valueTongTien = "";
				valueTongTien += "[";	
				for( int i=0; i<array.length;i++) {
					System.err.println(array[i]);
					String[] arra= array[i].split(",");
					valueNgay=valueNgay+ "\""+arra[0]+"\""+"," ;
					valueTongTien=valueTongTien+ arra[1]+",";
				}
				valueNgay=valueNgay.substring(0, valueNgay.length()-1);
				valueTongTien=valueTongTien.substring(0,valueTongTien.length()-1);
				valueNgay=valueNgay+"]";
				valueTongTien=valueTongTien+"]";
				model.addAttribute("valueDate", valueNgay);
				model.addAttribute("totalMoney", valueTongTien);
				
				return "admin/pages/analysis/analysis";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
		
	}
}
