package com.project.CarRental2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.CarRental2.constants.FiledName;
import com.project.CarRental2.model.Booking;
import com.project.CarRental2.model.Car;
import com.project.CarRental2.model.District;
import com.project.CarRental2.model.User;
import com.project.CarRental2.model.Ward;
import com.project.CarRental2.service.BookingService;
import com.project.CarRental2.service.CarService;
import com.project.CarRental2.service.DetailNotificationService;
import com.project.CarRental2.service.DistrictService;
import com.project.CarRental2.service.WardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin("*")
public class AjaxController implements FiledName {

	@Autowired
	private DistrictService districtService;

	@Autowired
	private WardService wardService;

	@Autowired
	private CarService carService;

	@Autowired
	private BookingService bookingService;

	@Autowired
	private DetailNotificationService detailNotificationService;

	@GetMapping("/getDistrict{id}")
	public List<District> getDistric1(@RequestParam("id") String id) {
		int a = Integer.valueOf(id);
		System.err.println(a);
		List<District> d = districtService.getAllDistrictByIdProvince(a);
		List<District> newlist = new ArrayList<>();

	
		for (District district : d) {
			District newDistrict = new District(district.getIdDistrict(), district.getNameDistrict());
			newlist.add(newDistrict);
		}
		return newlist;
	}

	@GetMapping("/getWard{id}")
	public List<Ward> getWards(@RequestParam("id") int id) {
		List<Ward> listWards = wardService.getAllWardByIdDistric(id);
		List<Ward> newlist = new ArrayList<>();
		for (Ward ward : listWards) {
			newlist.add(new Ward(ward.getIdWard(), ward.getNameWard()));
		}
		System.out.println(newlist);
		return newlist;

	}

	@GetMapping("/sum-car-has-driver{address}")
	public int getSumCarinAddressHasDriver(@RequestParam(name = "address") String address) {

		return carService.getSumCarInAddressHasDriver(address, HAS_DRIVERS);
	}

	@GetMapping("/sum-car-no-driver{address}")
	public int getSumCarinAddressNoDriver(@RequestParam(name = "address") String address) {

		return carService.getSumCarInAddressHasDriver(address, NO_DRIVERS);
	}

	@GetMapping("/check-date{idCar}{dateStart}{dateEnd}")
	public boolean checkBillOnTime(@RequestParam(name = "idCar") int idCar,
			@RequestParam(name = "dateStart") String dateStart,
			@RequestParam(name = "dateEnd", required = false) String dateEnd) {
		boolean exsit = false;
		String[] arrdateStart = dateStart.split("T");
		String[] arrdateEnd = dateEnd.split("T");

		System.out.println(arrdateStart[0]);
		System.out.println(arrdateEnd[0]);

		List<Booking> list = bookingService.checkBillExistOnTime(idCar, arrdateStart[0], arrdateEnd[0]);
		System.out.println(list.toString());
		if (list.size() > 0) {
			exsit = true;
		}
		return exsit;
	}

	@GetMapping("/sum-delivery-in-car{idCar}")
	public int sumDeliveryByCar(int idCar) {
		return bookingService.countDeliveryByIdCar(idCar);
	}

	@GetMapping("/filter-car")
	public List<Car> filterCar(@RequestParam(name = "address") String address,
			@RequestParam(name = "dateStart") String dateStart, @RequestParam("dateEnd") String dateEnd,
			HttpServletRequest request, @RequestParam(name = "driver") boolean driver) {
		List<Car> listCarWithNewAddress = new ArrayList<Car>();

		String[] arrdateStart = dateStart.split("T");
		String[] arrdateEnd = dateEnd.split("T");
		List<Car> listCar = new ArrayList<>();
		if (driver == true) {
			listCar = carService.findCarOnTimeByDriverAndAddress(HAS_DRIVERS, address, arrdateStart[0], arrdateEnd[0], STATUS_APPROVED);
		} else {
			listCar = carService.findCarOnTimeByDriverAndAddress(NO_DRIVERS, address, arrdateStart[0], arrdateEnd[0], STATUS_APPROVED);
		}
		listCarWithNewAddress = HomePageController.setListNewAddress(listCar);
		System.err.println(listCarWithNewAddress);

		return listCarWithNewAddress;
	}

	@GetMapping("/reading-notification/{idNoti}")
	public String readNotification(HttpServletRequest request, @PathVariable(name = "idNoti") int idNoti) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("sesionUser");
		System.err.println(user);
		detailNotificationService.updateStatusNotificationByIDNotiAndIDUser(READING, user.getIdUser(), idNoti);
		return "redirect:/";
	}
}
