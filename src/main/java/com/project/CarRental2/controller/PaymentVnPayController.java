package com.project.CarRental2.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.CarRental2.constants.FiledName;
import com.project.CarRental2.model.Booking;
import com.project.CarRental2.model.DetailNotification;
import com.project.CarRental2.model.Notification;
import com.project.CarRental2.model.PaymentBill;
import com.project.CarRental2.model.ResuftPayment;
import com.project.CarRental2.model.User;
import com.project.CarRental2.service.BookingService;
import com.project.CarRental2.service.DetailNotificationService;
import com.project.CarRental2.service.NotificationService;
import com.project.CarRental2.service.PaymentBillService;
import com.project.CarRental2.service.UserService;
import com.project.CarRental2.vnpay.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentVnPayController implements FiledName {

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PaymentBillService paymentBillService;
	
	@Autowired
	private NotificationService notificationService;

	@Autowired
	private DetailNotificationService detailNotificationService;

	@GetMapping("/payment-bill/{id}/")
	public String createPayment(HttpServletRequest req,
			@PathVariable(name = "id") int id)
			throws UnsupportedEncodingException {
		
		HttpSession session = req.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser==null) {
			return "redirect:/login";
		}else {
			session.setAttribute("idBooking", id);
			String orderType = req.getParameter("ordertype");
			long amount = Integer.parseInt(req.getParameter("amount")) * 100;
			String bankCode = req.getParameter("bankCode");

			String vnp_TxnRef = Config.getRandomNumber(8);
			String vnp_IpAddr = Config.getIpAddress(req);
			String vnp_TmnCode = Config.vnp_TmnCode;

			Map<String, String> vnp_Params = new HashMap<>();
			vnp_Params.put("vnp_Version", Config.vnp_Version);
			vnp_Params.put("vnp_Command", Config.vnp_Command);
			vnp_Params.put("vnp_TmnCode", Config.vnp_TmnCode);
			vnp_Params.put("vnp_Amount", String.valueOf(amount));
			vnp_Params.put("vnp_CurrCode", "VND");

			if (bankCode != null && !bankCode.isEmpty()) {
				vnp_Params.put("vnp_BankCode", bankCode);
			}
			vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
			vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
			vnp_Params.put("vnp_OrderType", orderType);

			String locate = req.getParameter("language");
			if (locate != null && !locate.isEmpty()) {
				vnp_Params.put("vnp_Locale", locate);
			} else {
				vnp_Params.put("vnp_Locale", "vn");
			}
			vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
			vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

			Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String vnp_CreateDate = formatter.format(cld.getTime());
			vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

			cld.add(Calendar.MINUTE, 15);
			String vnp_ExpireDate = formatter.format(cld.getTime());
			vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

			List fieldNames = new ArrayList(vnp_Params.keySet());
			Collections.sort(fieldNames);
			StringBuilder hashData = new StringBuilder();
			StringBuilder query = new StringBuilder();
			Iterator itr = fieldNames.iterator();
			while (itr.hasNext()) {
				String fieldName = (String) itr.next();
				String fieldValue = (String) vnp_Params.get(fieldName);
				if ((fieldValue != null) && (fieldValue.length() > 0)) {
					// Build hash data
					hashData.append(fieldName);
					hashData.append('=');
					hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
					// Build query
					query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
					query.append('=');
					query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
					if (itr.hasNext()) {
						query.append('&');
						hashData.append('&');
					}
				}
			}
			String queryUrl = query.toString();
			String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
			queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
			String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
			return "redirect:" + paymentUrl;
		}
	}

	@GetMapping("/payment-info")
	public String getPaymentInfo(@RequestParam("vnp_Amount") int totalBill,
			@RequestParam("vnp_BankCode") String bankCode, @RequestParam("vnp_ResponseCode") String vnp_ResponseCode,
			@RequestParam("vnp_BankTranNo") String vnp_BankTranNo, HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		int idBooking = (int) session.getAttribute("idBooking");
		PaymentBill paymentBill = new PaymentBill(0, bankCode, vnp_BankTranNo, vnp_ResponseCode, totalBill,
				sessionUser);
		if(sessionUser==null) {
			return "redirect:/login";
		}else {
			if (vnp_ResponseCode.equals("00")) {
				paymentBillService.savePaymentBill(paymentBill);
				try {
					Booking booking = bookingService.getABooking(idBooking);
					User u = userService.getAUser(booking.getCar().getUser().getIdUser());
					System.err.println("Sysem discount:"+ SYSTEM_DISCOUNT);
					System.err.println(u.getTotalMoney()+ booking.getBillTotal() - (booking.getBillTotal()*SYSTEM_DISCOUNT));
					double newTotalMoney= u.getTotalMoney()+ booking.getBillTotal() - (booking.getBillTotal()*SYSTEM_DISCOUNT);
					u.setTotalMoney((int) newTotalMoney);
					System.err.println(u.toString());
					DetailNotification detailNotification = new DetailNotification(0, NO_READING, "/my-bill", u,
							new Notification(0, "logo1.png", "Đơn hàng của bạn đã thanh toán",
									"Đơn đặt xe " + booking.getCar().getNameCar() + " của bạn đã thanh toán bởi "
											+ booking.getUser().getUsername() + "",
									new Date(), new Date()));
					notificationService.saveNotification(detailNotification.getNotification());
					detailNotificationService.saveDetail(detailNotification);
					try {
						System.err.println("Sau khi "+u);
						userService.updateTotalMoney(u.getTotalMoney(), u.getIdUser());
					} catch (JpaSystemException e) {
						e.printStackTrace();
					}
					bookingService.changeStatusBill(STATUS_PAYMENT, idBooking);

				} catch (JpaSystemException e) {
					e.printStackTrace();
				}
				model.addAttribute("resultPayment", "Thanh toán thành công");
			} else {
				model.addAttribute("resultPayment", "Thanh toán thất bại");
			}
			return "pages/result-payment";
		}
		
	}
}
