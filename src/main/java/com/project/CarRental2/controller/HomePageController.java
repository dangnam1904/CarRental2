package com.project.CarRental2.controller;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.project.CarRental2.model.Province;
import com.project.CarRental2.model.Role;
import com.project.CarRental2.model.User;
import com.project.CarRental2.model.Ward;
import com.project.CarRental2.service.CarService;
import com.project.CarRental2.service.DistrictService;
import com.project.CarRental2.service.EncryptionPassword;
import com.project.CarRental2.service.ProvinceService;
import com.project.CarRental2.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomePageController  implements FiledName{

	@Autowired
	private UserService userService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private DistrictService districtService;
	

	@GetMapping("/")
	public String HomePage(Model model) {
		model.addAttribute("provinces", provinceService.getAllProvinceOrderByName());
		List<Car> listCarHasDiver=carService.getAllCarByDriverOderByName(HAS_DRIVERS);
		List<Car> listCarHasDriverNewAddress= HomePageController.setListNewAddress(listCarHasDiver);
		List<Car> listCarNoDiver=carService.getAllCarByDriverOderByName(NO_DRIVERS);
		List<Car> listCarNoDriverNewAddress= HomePageController.setListNewAddress(listCarNoDiver);
		model.addAttribute("carHasDriver", listCarHasDriverNewAddress);
		model.addAttribute("carNoDriver", listCarNoDriverNewAddress);
		return "index";
	}
	
	public static List<Car> setListNewAddress(List<Car> listCar) {
		for (Car car : listCar) {
			String[] adrr= car.getAddressCar().split(",");
			String newAddress="";
			for( int i=adrr.length-2;i<adrr.length;i++) {
				newAddress= newAddress+ adrr[i]+",";
			}
			newAddress= newAddress.substring(0,newAddress.length()-1);
			car.setAddressCar(newAddress);
		}
		return listCar;
	}

	@GetMapping("/car-address")
	public String carAddress() {
		return "pages/address-car";
	}
	
	@GetMapping("/{id}/{address}/has-driver")
	public String getCarWithAddressHasDriver(Model model,
			@PathVariable(name="id") int id_address, @PathVariable(name="address") String address ) {
		
		List<Car> listCar_promotional_price= carService.getAllCarByDriverInAddressAndPromotionalPriceOderByName(HAS_DRIVERS, address);
		model.addAttribute("carPromotinalPrice", HomePageController.setListNewAddress(listCar_promotional_price));
		model.addAttribute("address", address.toUpperCase());
		List<Car> listCar= carService.getAllCarByDriverInAddressOrderByName(HAS_DRIVERS, address);
		model.addAttribute("listCar", HomePageController.setListNewAddress(listCar));
		model.addAttribute("distric", districtService.getAllDistrictByIdProvince(id_address));
		return "pages/address-car";
	}
	
	@PostMapping("/booking-car")
	public String booking(Model model, @RequestParam(name="dateStart") String dateStart, @RequestParam(name="dateEnd") String dateEnd,
			 @RequestParam(name="idCar") int id_car, @RequestParam(name="input_total_bill") int totalBill,  
			 @RequestParam(name="phone") String phone, @RequestParam(name="province") Province province, 
			 @RequestParam(name="district") District district, @RequestParam(name="ward") Ward ward, 
			 @RequestParam(name="address-detail") String addressDetail, HttpServletRequest request) {
		System.out.println(id_car);
		System.out.println(dateStart);
		System.out.println(dateEnd);
		System.out.println(totalBill);
		System.out.println(phone);
		System.out.println(province.toString());
		System.out.println(district.toString());
		System.out.println(ward.toString());
		System.out.println(addressDetail);
		
		Booking booking= new Booking();
	
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		 Date datestart= null;
		 Date dateend= null;
		try {
			datestart = dateFormat.parse(dateStart);
			dateend = dateFormat.parse(dateEnd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		booking.setDateStart(datestart);
		booking.setDateEnd(dateend);
		Car car= new Car(id_car);
		booking.setCar(car);
		System.err.println(booking.toString());
		booking.setCreateDate(new Date());
		booking.setUpdateDate(new Date());
		booking.setBillTotal(totalBill);
		booking.setStatusBill(STATUS_PENDING);
		booking.setPhone(phone);
		booking.setAddress(addressDetail+","+ ward.getNameWard()+","+ district.getNameDistrict()+","+ province.getNameProvince());
		System.err.println(booking.toString());
		return "u";
	}
	
	@GetMapping("/{id}/{address}/no-driver")
	public String getCarWithAddressNoDriver(Model model,@PathVariable(name="id") int id_province,
			@PathVariable(name="address") String address ) {
		
		List<Car> listCar_promotional_price= carService.getAllCarByDriverInAddressAndPromotionalPriceOderByName(NO_DRIVERS, address);
		System.err.println(listCar_promotional_price.toString());
		model.addAttribute("carPromotinalPrice", HomePageController.setListNewAddress(listCar_promotional_price));
		model.addAttribute("address", address.toUpperCase());
		List<Car> listCar= carService.getAllCarByDriverInAddressOrderByName(NO_DRIVERS, address);
		System.err.println(listCar.toString());
		model.addAttribute("listCar", HomePageController.setListNewAddress(listCar));
		model.addAttribute("distric", districtService.getAllDistrictByIdProvince(id_province));
		return "pages/address-car";
	}
	


	@GetMapping("/car-detail/{id}/{nameCar}")
	public String carDetail(Model  model,@PathVariable(name="id") int id_car, @PathVariable(name="nameCar") String namCar) {
		model.addAttribute("car", carService.getACarByIdCar(id_car));
		System.err.println(userService.getAUser(carService.getACarByIdCar(id_car).getUser().getIdUser()));
		model.addAttribute("user", userService.getAUser(carService.getACarByIdCar(id_car).getUser().getIdUser()));
		String[] adrr= carService.getACarByIdCar(id_car).getAddressCar().split(",");
		String newAddress="";
		for( int i=adrr.length-2;i<adrr.length;i++) {
			newAddress= newAddress+ adrr[i]+",";
		}
		newAddress= newAddress.substring(0,newAddress.length()-1);
		model.addAttribute("address", newAddress);
		model.addAttribute("listCar", HomePageController.setListNewAddress( carService.getAllCarOrderByNameCarAsc()));
		model.addAttribute("province", provinceService.getAllProvinceOrderByName());
		model.addAttribute("booking", new Booking());
		return "pages/car-detail";
	}

	@GetMapping("/filter")
	public String filterCar() {
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
				sessionUser.setAttribute("sessionUser", user2);
				System.out.println(sessionUser.getAttribute("sessionUser"));
				if(user2.getRole().getIdRole()==ROLE_USER) {
					return "redirect:/";
				}else if(user2.getRole().getIdRole()==ROLE_ADMIN){
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
	public String logout(HttpSession  session) {
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

	

}
