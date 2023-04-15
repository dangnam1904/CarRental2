package com.project.CarRental2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.CarRental2.constants.FiledName;
import com.project.CarRental2.model.Booking;
import com.project.CarRental2.model.District;
import com.project.CarRental2.model.Ward;
import com.project.CarRental2.service.BookingService;
import com.project.CarRental2.service.CarService;
import com.project.CarRental2.service.DistrictService;
import com.project.CarRental2.service.WardService;

@RestController
public class AjaxController implements FiledName {

	@Autowired
	private DistrictService districtService;

	@Autowired
	private WardService wardService;

	@Autowired
	private CarService carService;
	@Autowired
	private BookingService bookingService;

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
		if (list.size()>0) {
			exsit = true;
		}
		return exsit;
	}
}
