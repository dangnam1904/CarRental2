package com.project.CarRental2.controller;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.CarRental2.constants.FiledName;
import com.project.CarRental2.model.Booking;
import com.project.CarRental2.model.Car;
import com.project.CarRental2.model.DetailNotification;
import com.project.CarRental2.model.District;
import com.project.CarRental2.model.Insurance;
import com.project.CarRental2.model.Notification;
import com.project.CarRental2.model.Province;
import com.project.CarRental2.model.RequestWithdrawal;
import com.project.CarRental2.model.Role;
import com.project.CarRental2.model.User;
import com.project.CarRental2.model.Ward;
import com.project.CarRental2.service.BlogService;
import com.project.CarRental2.service.BookingService;
import com.project.CarRental2.service.CarService;
import com.project.CarRental2.service.ContractService;
import com.project.CarRental2.service.DetailNotificationService;
import com.project.CarRental2.service.DistrictService;
import com.project.CarRental2.service.EncryptionPassword;
import com.project.CarRental2.service.InsuranceService;
import com.project.CarRental2.service.NotificationService;
import com.project.CarRental2.service.ProvinceService;
import com.project.CarRental2.service.RequestWithdrawalService;
import com.project.CarRental2.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomePageController implements FiledName {
	

	@Autowired
	private UserService userService;

	@Autowired
	private ProvinceService provinceService;

	@Autowired
	private CarService carService;

	@Autowired
	private DistrictService districtService;

	@Autowired
	private BookingService bookingService;

	@Autowired
	private BlogService blogService;

	@Autowired
	private InsuranceService insuranceService;

	@Autowired
	private ContractService contractService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private DetailNotificationService detailNotificationService;
	
	@Autowired
	private RequestWithdrawalService requestWithdrawalService;

	private int idUserOwnerCar = 0;

	@GetMapping("/reading-notification")
	public String readNotification(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User useSession = (User) session.getAttribute("sesionUser");

		if (useSession == null) {
			return "redirect:/login";
		}
		detailNotificationService.updateStatusAllByIDUserNotification(READING, useSession.getIdUser());
		return "redirect:/";
	}

	@GetMapping("/")
	public String HomePage(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("sesionUser");
		List<DetailNotification> listDetailNotification = new ArrayList<DetailNotification>();
		if (user != null) {
			listDetailNotification = detailNotificationService.getListDetailNotification(user.getIdUser());
		}
		model.addAttribute("listDetailNotification", listDetailNotification);
		model.addAttribute("provinces", provinceService.getAllProvinceOrderByName());
		List<Car> listCarHasDiver = carService.getAllCarByDriverAndStatusCarOderByName(HAS_DRIVERS, STATUS_APPROVED);
		List<Car> listCarHasDriverNewAddress = HomePageController.setListNewAddress(listCarHasDiver);
		List<Car> listCarNoDiver = carService.getAllCarByDriverAndStatusCarOderByName(NO_DRIVERS, STATUS_APPROVED);
		List<Car> listCarNoDriverNewAddress = HomePageController.setListNewAddress(listCarNoDiver);
		List<Insurance> listInsurances = insuranceService.getAllInsurance();
		model.addAttribute("contentInsurances", listInsurances.get(0).getContentInsurance());
		model.addAttribute("listInsurances", listInsurances);
		model.addAttribute("carHasDriver", listCarHasDriverNewAddress);
		model.addAttribute("carNoDriver", listCarNoDriverNewAddress);
		return "index";
	}

	public static List<Car> setListNewAddress(List<Car> listCar) {
		for (Car car : listCar) {
			String[] adrr = car.getAddressCar().split(",");
			String newAddress = "";
			for (int i = adrr.length - 2; i < adrr.length; i++) {
				newAddress = newAddress + adrr[i] + ",";
			}
			newAddress = newAddress.substring(0, newAddress.length() - 1);
			car.setAddressCar(newAddress);
		}
		return listCar;
	}

	@GetMapping("/car-address")
	public String carAddress() {
		return "pages/address-car";
	}

	@GetMapping("/help")
	public String getHelp() {
		return "pages/help";
	}

	@GetMapping("/help-Ower-Car")
	public String getHelpOwerCar() {
		return "pages/help-ower-car";
	}

	@GetMapping(path = { "/contact", "/lien-he" })
	public String getContact() {
		return "pages/contact";
	}

	@GetMapping("/{id}/{address}/has-driver")
	public String getCarWithAddressHasDriver(Model model, @PathVariable(name = "id") int id_address,
			@PathVariable(name = "address") String address) {

		List<Car> listCar_promotional_price = carService
				.getAllCarByDriverInAddressAndPromotionalPriceOderByName(HAS_DRIVERS, address);
		model.addAttribute("carPromotinalPrice", HomePageController.setListNewAddress(listCar_promotional_price));
		model.addAttribute("address", address.toUpperCase());
		List<Car> listCar = carService.getAllCarByDriverInAddressOrderByName(HAS_DRIVERS, address);
		model.addAttribute("listCar", HomePageController.setListNewAddress(listCar));
		model.addAttribute("distric", districtService.getAllDistrictByIdProvince(id_address));
		return "pages/address-car";
	}

	@PostMapping("/booking-car")
	public String booking(Model model, @RequestParam(name = "dateStart") String dateStart,
			@RequestParam(name = "dateEnd") String dateEnd, @RequestParam(name = "idCar") int id_car,
			@RequestParam(name = "input_total_bill") int totalBill, @RequestParam(name = "phone") String phone,
			@RequestParam(name = "province") Province province, @RequestParam(name = "district") District district,
			@RequestParam(name = "ward") Ward ward, @RequestParam(name = "address-detail") String addressDetail,
			HttpServletRequest request, RedirectAttributes ra) {
		System.err.println("Ngay " + dateStart);
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("sesionUser");
		
		User sessionUser= (User) session.getAttribute("sesionUser");
		System.err.println(sessionUser);
		if (user == null) {
			ra.addFlashAttribute("messege_error", "Chưa đăng nhập");
			return "redirect:/login";
		}
		if(sessionUser.getImgDrivingLicense()==null || sessionUser.getDrivingLicense()==null || sessionUser.getPhone()==null ) {
			ra.addFlashAttribute("messege_error", "Bạn chưa cập nhật bằng lái xe");
			return "redirect:/edit-profile";
		}
		Booking booking = new Booking();
		booking.setUser(user);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date datestart = null;
		Date dateend = null;
		try {
			datestart = dateFormat.parse(dateStart);
			dateend = dateFormat.parse(dateEnd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		booking.setDateStart(datestart);
		booking.setDateEnd(dateend);
		Car car = carService.getACarByIdCar(id_car);
		booking.setCar(car);
		booking.setCreateDate(new Date());
		booking.setUpdateDate(new Date());
		booking.setBillTotal(totalBill);
		booking.setStatusBill(STATUS_PENDING);
		booking.setPhone(phone);
		booking.setAddress(addressDetail + "," + ward.getNameWard() + "," + district.getNameDistrict() + ","
				+ province.getNameProvince());
		System.err.println(booking.toString());
		System.err.println("  trrt:" + booking.getCar().getAvatarCar());
		Notification notification = new Notification(0, car.getAvatarCar(), "Bạn có đơn đặt xe mới",
				"" + user.getNameDisplay() + " đã đặt xe của bạn, vui lòng xác nhận đơn hàng của bạn", new Date(),
				new Date());
		notificationService.saveNotification(notification);
		Notification lasterNotification = notificationService.getNotificationLaster();
		DetailNotification detailNotification = new DetailNotification(0, NO_READING, "/my-bill",
				userService.getUserByIdCar(booking.getCar().getIdCar()), lasterNotification);
		detailNotificationService.saveDetail(detailNotification);
		bookingService.saveBooking(booking);
		return "redirect:/my-trip";
	}

	@GetMapping("/{id}/{address}/no-driver")
	public String getCarWithAddressNoDriver(Model model, @PathVariable(name = "id") int id_province,
			@PathVariable(name = "address") String address) {

		List<Car> listCar_promotional_price = carService
				.getAllCarByDriverInAddressAndPromotionalPriceOderByName(NO_DRIVERS, address);
		System.err.println(listCar_promotional_price.toString());
		model.addAttribute("carPromotinalPrice", HomePageController.setListNewAddress(listCar_promotional_price));
		model.addAttribute("address", address.toUpperCase());
		List<Car> listCar = carService.getAllCarByDriverInAddressOrderByName(NO_DRIVERS, address);
		System.err.println(listCar.toString());
		model.addAttribute("listCar", HomePageController.setListNewAddress(listCar));
		model.addAttribute("distric", districtService.getAllDistrictByIdProvince(id_province));
		return "pages/address-car";
	}

	@GetMapping("/car-detail/{id}/{nameCar}")
	public String carDetail(Model model, @PathVariable(name = "id") int id_car,
			@PathVariable(name = "nameCar") String namCar) {
		model.addAttribute("car", carService.getACarByIdCar(id_car));
		System.err.println(userService.getAUser(carService.getACarByIdCar(id_car).getUser().getIdUser()));
		model.addAttribute("user", userService.getAUser(carService.getACarByIdCar(id_car).getUser().getIdUser()));
		String[] adrr = carService.getACarByIdCar(id_car).getAddressCar().split(",");
		String newAddress = "";
		for (int i = adrr.length - 2; i < adrr.length; i++) {
			newAddress = newAddress + adrr[i] + ",";
		}
		newAddress = newAddress.substring(0, newAddress.length() - 1);
		model.addAttribute("address", newAddress);
		model.addAttribute("listCar", HomePageController.setListNewAddress(carService.getAllCarOrderByNameCarAsc()));
		model.addAttribute("province", provinceService.getAllProvinceOrderByName());
		model.addAttribute("booking", new Booking());
		return "pages/car-detail";
	}

	@GetMapping("/filter")
	public String filterCar(Model model, @RequestParam(name = "address") String address,
			@RequestParam(name = "dateStart") String dateStart, @RequestParam("dateEnd") String dateEnd,
			HttpServletRequest request, @RequestParam(name = "driver") boolean driver) {
		model.addAttribute("address", address);
		model.addAttribute("dateStart", dateStart);
		model.addAttribute("dateEnd", dateEnd);
		String[] arrdateStart = dateStart.split("T");
		String[] arrdateEnd = dateEnd.split("T");
		List<Car> listCar = new ArrayList<>();
		if (driver == true) {
			listCar = carService.findCarOnTimeByDriverAndAddress(HAS_DRIVERS, address, arrdateStart[0], arrdateEnd[0],STATUS_APPROVED);
		} else {
			listCar = carService.findCarOnTimeByDriverAndAddress(NO_DRIVERS, address, arrdateStart[0], arrdateEnd[0],STATUS_APPROVED);
		}
		List<Car> listCarWithNewAddress = HomePageController.setListNewAddress(listCar);
		System.err.println(listCarWithNewAddress);
		model.addAttribute("listCar", listCarWithNewAddress);
		model.addAttribute("car", new Car());
		return "pages/filter-car";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		System.err.println( EncryptionPassword.encryption("admin"));
		return "pages/login";
	}

	@PostMapping("/login")
	public String checkLogin(@ModelAttribute("user") User user, HttpServletRequest request,
			RedirectAttributes rAttributes) {
		String url = "";
		HttpSession sessionUser = request.getSession();
		user.setPassword(EncryptionPassword.encryption(user.getPassword()));
		List<User> list = userService.getAllUserOrderByUsername();
		for (User user2 : list) {
			if (user.getUsername().equals(user2.getUsername()) && user.getPassword().equals(user2.getPassword())) {
				sessionUser.setAttribute("sesionUser", user2);
				System.out.println(sessionUser.getAttribute("sesionUser"));
				if (user2.getRole().getIdRole() == ROLE_USER) {
					return "redirect:/";
				} else if (user2.getRole().getIdRole() == ROLE_ADMIN) {
					return "redirect:/admin";
				}

			} else {
				rAttributes.addFlashAttribute("mes", "Tài khoản không chính xác");
				url = "redirect:/login";
			}
		}
		return url;
	}

	@GetMapping("/sign-up")
	public String registerUser(Model model) {
		model.addAttribute("user", new User());
		return "pages/resgiter";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@PostMapping("/sign-up")
	public String register_User(@ModelAttribute("user") User user, RedirectAttributes rAttributes) throws SQLException {
		user.setPassword(EncryptionPassword.encryption(user.getPassword()));

		String url = "";
		System.out.println(user.getPassword());
		user.setCreateDate(new Date());
		user.setUpdateDate(new Date());
		user.setRole(new Role(ROLE_USER));
		user.getRole().getIdRole();
		System.out.println(user);
		List<User> list = userService.getAllUserOrderByUsername();
		System.out.println(list.toString());
		for (int i = 0; i < list.size(); i++) {
			if (user.getUsername().equals(list.get(i).getUsername())) {
				rAttributes.addFlashAttribute("mes", "Taì khoản đã tồn tại");
				url = "redirect:/sign-up";
				break;

			} else {
				userService.saveUser(user);
				rAttributes.addFlashAttribute("mes", "Đăng kí thành công");
				url = "redirect:/login";
			}
		}
		return url;
	}

	@GetMapping("/header")
	public String he() {
		return "pages/layout/header";
	}

	@GetMapping("/edit-profile")
	public String editProfile(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User useSession = (User) session.getAttribute("sesionUser");
		if (useSession == null) {
			return "redirect:/login";
		}
		model.addAttribute("user", useSession);
		model.addAttribute("province", provinceService.getAllProvinceOrderByName());
		return "pages/edit-profile";
	}

	@GetMapping("/my-car")
	public String myCar(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User useSession = (User) session.getAttribute("sesionUser");
		if (useSession == null) {
			return "redirect:/login";
		}
		model.addAttribute("user", useSession);
		model.addAttribute("listCar", carService.getAllCarByIdUser(useSession.getIdUser()));
		return "pages/my-car";
	}

	@GetMapping("/my-bill")
	public String myBill(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User useSession = (User) session.getAttribute("sesionUser");
		if (useSession == null) {
			return "redirect:/login";
		}
		model.addAttribute("user", useSession);
		model.addAttribute("listBooking", bookingService.getAllBookingWithCarOwner(useSession.getIdUser()));
		return "pages/my-bill";
	}

	@GetMapping("/my-trip")
	public String myTrip(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User useSession = (User) session.getAttribute("sesionUser");
		if (useSession == null) {
			return "redirect:/login";
		}
		model.addAttribute("user", useSession);
		List<Booking> list = bookingService.getAllBookingWithIdUserLessee(useSession.getIdUser());
		try {
			idUserOwnerCar = list.get(0).getCar().getUser().getIdUser();
		} catch (IndexOutOfBoundsException e) {

		}

		model.addAttribute("listBooking", list);
		return "pages/my-trip";
	}

	@GetMapping("/approved-bill/{id}")
	public String changStatusApprovedBill(@PathVariable(name = "id") int id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User useSession = (User) session.getAttribute("sesionUser");
		if (useSession == null) {
			return "redirect:/login";
		}
		try {
			Booking booking = bookingService.getABooking(id);
			DetailNotification detailNotification = new DetailNotification(0, NO_READING, "/my-trip", booking.getUser(),
					new Notification(0, "logo1.png", "Đơn hàng của bạn đã được xác nhận",
							"Đơn đặt xe " + booking.getCar().getNameCar() + " của bạn đã được xác nhận", new Date(),
							new Date()));
			System.err.println(detailNotification.toString());
			notificationService.saveNotification(detailNotification.getNotification());
			detailNotificationService.saveDetail(detailNotification);
			;
			bookingService.changeStatusBill(STATUS_APPROVED, id);
		} catch (JpaSystemException e) {
			System.err.println("ex");
		}

		return "redirect:/my-bill";
	}

	@GetMapping("/cancel-bill/{id}")
	public String changStatusCancelBill(@PathVariable(name = "id") int id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User useSession = (User) session.getAttribute("sesionUser");
		if (useSession == null) {
			return "redirect:/login";
		}
		try {
			Booking booking = bookingService.getABooking(id);
			DetailNotification detailNotification = new DetailNotification(0, NO_READING, "/my-trip", booking.getUser(),
					new Notification(0, "logo1.png", "Đơn hàng của bạn đã không được chấp nhận",
							"Đơn đặt xe " + booking.getCar().getNameCar() + " của bạn đã không được chấp nhận",
							new Date(), new Date()));
			notificationService.saveNotification(detailNotification.getNotification());
			detailNotificationService.saveDetail(detailNotification);
			bookingService.changeStatusBill(STATUS_CANCAL, id);
		} catch (JpaSystemException e) {
			System.err.println("ex");
		}

		return "redirect:/my-bill";
	}

	@GetMapping("/cancel-bill-trip/{id}")
	public String changStatusCancelBillUser(@PathVariable(name = "id") int id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User useSession = (User) session.getAttribute("sesionUser");
		if (useSession == null) {
			return "redirect:/login";
		}
		try {
			bookingService.changeStatusBill(STATUS_CANCAL, id);
		} catch (JpaSystemException e) {
			System.err.println("ex");
		}

		return "redirect:/my-trip";
	}

	@GetMapping("/restore-bill/{id}")
	public String changStatusRestoreBill(@PathVariable(name = "id") int id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User useSession = (User) session.getAttribute("sesionUser");
		if (useSession == null) {
			return "redirect:/login";
		}
		try {
			bookingService.changeStatusBill(STATUS_PENDING, id);
		} catch (JpaSystemException e) {
			e.printStackTrace();
		}

		return "redirect:/my-bill";
	}

	@GetMapping("/payment-bill/{id}")
	public String changStatusPaymentBill(@PathVariable(name = "id") int id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User useSession = (User) session.getAttribute("sesionUser");
		if (useSession == null) {
			return "redirect:/login";
		}
		try {
			System.err.println("fdjgkdgk");
			Booking booking = bookingService.getABooking(id);
			User u = userService.getAUser(idUserOwnerCar);
			double totalMoney=u.getTotalMoney() +booking.getBillTotal()- (booking.getBillTotal() * SYSTEM_DISCOUNT);
			u.setTotalMoney((int) totalMoney);
			DetailNotification detailNotification = new DetailNotification(0, NO_READING, "/my-bill", u,
					new Notification(0, "logo1.png", "Đơn hàng của bạn đã thanh toán",
							"Đơn đặt xe " + booking.getCar().getNameCar() + " của bạn đã thanh toán bởi "
									+ booking.getUser().getUsername() + "",
							new Date(), new Date()));
			notificationService.saveNotification(detailNotification.getNotification());
			detailNotificationService.saveDetail(detailNotification);
			try {
				userService.updateTotalMoney(u.getTotalMoney(), u.getIdUser());
			} catch (JpaSystemException e) {
			}
			bookingService.changeStatusBill(STATUS_PAYMENT, id);

		} catch (JpaSystemException e) {
			e.printStackTrace();
		}
		return "redirect:/my-trip";
	}

	@GetMapping("/blog")
	public String getAll(Model model) {
		model.addAttribute("blogs", blogService.getAllBlog());
		return "pages/blog";
	}

	@GetMapping("/blog/{id}/{title-blog}")
	public String getdetailBlog(Model model, @PathVariable(name = "id") int id) {
		model.addAttribute("blog", blogService.getBlogById(id));
		model.addAttribute("blogs", blogService.getAllBlog());
		return "pages/detail-blog";
	}

	@GetMapping("/my-contract")
	public String getContract(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("sesionUser");
		if (user == null) {
			return "redirect:/login";
		}
		model.addAttribute("user", user);
		model.addAttribute("sessionUser", user);
		model.addAttribute("listContract", contractService.getAllContract());
		List<RequestWithdrawal> listRequestWithdrawals=
				requestWithdrawalService.getAllRequestWithdrawByIdUserOrderByCreateDate(user.getIdUser());
		model.addAttribute("listRequestWithdraw", listRequestWithdrawals);
		return "pages/my-contract";
	}

	@GetMapping({ "/my-walet", "/analysis"})
	public String geMytWalet(Model model, HttpServletRequest request,
			@RequestParam(name="dateStart", required = false) String dateStart,
			@RequestParam(name="dateEnd", required = false) String dateEnd) {
		System.err.println(dateStart);
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("sesionUser");
		if (user == null) {
			return "redirect:/login";
		}
		model.addAttribute("user", user);
		model.addAttribute("sessionUser", user);
		List<RequestWithdrawal> listRequestWithdrawals=
				requestWithdrawalService.getAllRequestWithdrawByIdUserOrderByCreateDate(user.getIdUser());
		model.addAttribute("listRequestWithdraw", listRequestWithdrawals);
		model.addAttribute("requestWithdrawal", new RequestWithdrawal());
		
		if( dateEnd !=null && dateStart != null) {
			model.addAttribute("listBooking", bookingService.getAllBookingOnTimeByIdUserHaveCar(dateStart, dateEnd,
					STATUS_PAYMENT, user.getIdUser()));
			String[] array = bookingService.sumRevenueOnTimeByIdUser(dateStart, dateEnd, STATUS_PAYMENT, user.getIdUser());
			String valueNgay = "";
			valueNgay += "[";
			String valueTongTien = "";
			valueTongTien += "[";
			for (int i = 0; i < array.length; i++) {
				System.err.println(array[i]);
				String[] arra = array[i].split(",");
				valueNgay = valueNgay + "\"" + arra[0] + "\"" + ",";
				valueTongTien = valueTongTien + arra[1] + ",";
			}
			valueNgay = valueNgay.substring(0, valueNgay.length() - 1);
			valueTongTien = valueTongTien.substring(0, valueTongTien.length() - 1);
			valueNgay = valueNgay + "]";
			valueTongTien = valueTongTien + "]";
			model.addAttribute("valueDate", valueNgay);
			model.addAttribute("totalMoney", valueTongTien);
		}
		return "pages/my-walet";
	}

	
	
	@GetMapping({ "/withdraw" })
	public String requestWithdrawMoney(Model model, HttpServletRequest request,
			@ModelAttribute("requestWithdrawal") RequestWithdrawal requestWithdrawal) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("sesionUser");
		if (user == null) {
			return "redirect:/login";
		}
		model.addAttribute("user", user);
		model.addAttribute("sessionUser", user);
		requestWithdrawal.setCreateDate(new Date());
		requestWithdrawal.setUpdateDate(new Date());
		requestWithdrawal.setStatusRequest(STATUS_PENDING);
		requestWithdrawal.setUser(user);
		requestWithdrawal.setNameAccount(requestWithdrawal.getNameAccount().toUpperCase());
		requestWithdrawalService.saveRequestWithdrawal(requestWithdrawal);
		user.setTotalMoney(user.getTotalMoney()-requestWithdrawal.getMoneyNumber());
		userService.saveUser(user);
		return "redirect:/my-walet";
	}
}
