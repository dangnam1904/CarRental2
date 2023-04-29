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
import com.project.CarRental2.model.District;
import com.project.CarRental2.model.Insurance;
import com.project.CarRental2.model.Province;
import com.project.CarRental2.model.Role;
import com.project.CarRental2.model.User;
import com.project.CarRental2.model.Ward;
import com.project.CarRental2.service.BlogService;
import com.project.CarRental2.service.BookingService;
import com.project.CarRental2.service.CarService;
import com.project.CarRental2.service.DistrictService;
import com.project.CarRental2.service.EncryptionPassword;
import com.project.CarRental2.service.InsuranceService;
import com.project.CarRental2.service.ProvinceService;
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
	private InsuranceService  insuranceService;

	private int idUserOwnerCar = 0;

	@GetMapping("/")
	public String HomePage(Model model) {
		model.addAttribute("provinces", provinceService.getAllProvinceOrderByName());
		List<Car> listCarHasDiver = carService.getAllCarByDriverOderByName(HAS_DRIVERS);
		List<Car> listCarHasDriverNewAddress = HomePageController.setListNewAddress(listCarHasDiver);
		List<Car> listCarNoDiver = carService.getAllCarByDriverOderByName(NO_DRIVERS);
		List<Car> listCarNoDriverNewAddress = HomePageController.setListNewAddress(listCarNoDiver);
		List<Insurance> listInsurances = insuranceService.getAllInsurance();
		model.addAttribute("contentInsurances", listInsurances.get(0).getContentInsurance());
		model.addAttribute("listInsurances",listInsurances);
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
		User user = (User) session.getAttribute("user");
		if (user == null) {
			ra.addFlashAttribute("messege_error", "Chưa đăng nhập");
			return "redirect:/login";
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
		Car car = new Car(id_car);
		booking.setCar(car);
		booking.setCreateDate(new Date());
		booking.setUpdateDate(new Date());
		booking.setBillTotal(totalBill);
		booking.setStatusBill(STATUS_PENDING);
		booking.setPhone(phone);
		booking.setAddress(addressDetail + "," + ward.getNameWard() + "," + district.getNameDistrict() + ","
				+ province.getNameProvince());
		System.err.println(booking.toString());
		bookingService.saveBooking(booking);
		return "pages/layout/header";
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
		List<Car> listCar= new ArrayList<>();
		if(driver==true) {
			listCar= carService.findCarOnTimeByDriverAndAddress(HAS_DRIVERS, address, arrdateStart[0], arrdateEnd[0]);
		}else {
			listCar= carService.findCarOnTimeByDriverAndAddress(NO_DRIVERS, address, arrdateStart[0], arrdateEnd[0]);
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
				sessionUser.setAttribute("user", user2);
				System.out.println(sessionUser.getAttribute("user"));
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
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		model.addAttribute("province", provinceService.getAllProvinceOrderByName());
		return "pages/edit-profile";
	}

	@GetMapping("/my-car")
	public String myCar(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		model.addAttribute("listCar", carService.getAllCarByIdUser(user.getIdUser()));
		return "pages/my-car";
	}

	@GetMapping("/my-bill")
	public String myBill(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		List<Booking> list = bookingService.getAllBookingWithCarOwner(user.getIdUser());
		model.addAttribute("listBooking", bookingService.getAllBookingWithCarOwner(user.getIdUser()));
		return "pages/my-bill";
	}

	@GetMapping("/my-trip")
	public String myTrip(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		List<Booking> list = bookingService.getAllBookingWithIdUser(user.getIdUser());
		try {
			idUserOwnerCar = list.get(0).getCar().getUser().getIdUser();
		} catch (IndexOutOfBoundsException e) {

		}

		model.addAttribute("listBooking", list);
		return "pages/my-trip";
	}

	@GetMapping("/approved-bill/{id}")
	public String changStatusApprovedBill(@PathVariable(name = "id") int id) {
		try {
			bookingService.changeStatusBill(STATUS_APPROVED, id);
		} catch (JpaSystemException e) {
			System.err.println("ex");
		}

		return "redirect:/my-bill";
	}

	@GetMapping("/cancel-bill/{id}")
	public String changStatusCancelBill(@PathVariable(name = "id") int id) {
		try {
			bookingService.changeStatusBill(STATUS_CANCAL, id);
		} catch (JpaSystemException e) {
			System.err.println("ex");
		}

		return "redirect:/my-bill";
	}
	
	@GetMapping("/cancel-bill-trip/{id}")
	public String changStatusCancelBillUser(@PathVariable(name = "id") int id) {
		try {
			bookingService.changeStatusBill(STATUS_CANCAL, id);
		} catch (JpaSystemException e) {
			System.err.println("ex");
		}

		return "redirect:/my-trip";
	}

	@GetMapping("/restore-bill/{id}")
	public String changStatusRestoreBill(@PathVariable(name = "id") int id) {
		try {
			bookingService.changeStatusBill(STATUS_PENDING, id);
		} catch (JpaSystemException e) {
			e.printStackTrace();
		}

		return "redirect:/my-bill";
	}

	@GetMapping("/payment-bill/{id}")
	public String changStatusPaymentBill(@PathVariable(name = "id") int id) {
		try {
			Booking booking = bookingService.getABooking(id);
			User u = userService.getAUser(idUserOwnerCar);
			u.setTotalMoney(u.getTotalMoney() + booking.getBillTotal());
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
	public String getAll( Model model) {
		model.addAttribute("blogs", blogService.getAllBlog());
		return "pages/blog";
	}

	@GetMapping("/blog/{id}/{title-blog}")
	public String getdetailBlog( Model model, @PathVariable(name = "id") int id) {
		model.addAttribute("blog", blogService.getBlogById(id));
		model.addAttribute("blogs", blogService.getAllBlog());
		return "pages/detail-blog";
	}
	
}
