package com.project.CarRental2.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.CarRental2.model.Booking;
import com.project.CarRental2.repository.BookingRepository;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired BookingRepository repo;
	
	@Override
	public void saveBooking(Booking booking) {
		repo.save(booking);
	}

	@Override
	public Booking getABooking(int idBooking) {
		Optional<Booking> optional= repo.findById(idBooking);
		Booking booking= null;
		if(optional.isPresent()) {
			booking= optional.get();
		}
		return booking;
	}

	@Override
	public void deleteBooking(int idBooking) {
		repo.deleteById(idBooking);
	}

	@Override
	public List<Booking> getAllBooking() {
	
		return repo.findAll();
	}

	@Override
	public void changeStatusBill(int satatuBill, int idBooking) {
		repo.changeStatusBill(satatuBill, idBooking);	
	}

	@Override
	public List<Booking> getAllBookingWithIdUserLessee(int idUser) {
		return repo.findBookingByUserIdUserOrderByDateStartDesc(idUser);
	}

	@Override
	public List<Booking> getAllBookingWithCarOwner(int idUser) {
		
		return repo.getAllBookingWithCarOwner(idUser);
	}

	@Override
	public List<Booking> checkItemInTime(int idCar, Date dateStart, Date dateEnd) {
		
		return repo.findBookingByCarIdCarAndDateStartGreaterThanEqualAndDateEndLessThanEqual(idCar, dateStart, dateEnd);
	}

	@Override
	public List<Booking> checkBillExistOnTime(int idCar, String dateStart, String dateEnd) {
		
		return repo.checkBillExistOnTime(idCar, dateStart, dateEnd);
	}

	@Override
	public int countDeliveryByIdCar(int idCar) {
		
		return repo.countByCarIdCar(idCar);
	}

	@Override
	public List<Booking> getAllBookingOnTime(String dateStart, String dateEnd) {
		
		return repo.getAllBookingOnTime(dateStart, dateEnd);
	}

	@Override
	public int countBill() {
		
		return (int) repo.count();
	}

	@Override
	public List<Booking> getAllBookingByStatusBill(int statusBill) {
		
		return repo.findBookingByStatusBill(statusBill);
	}

	@Override
	public String[] sumRevenueOnTime(String dateStart, String dateEnd, int statusBill) {
		
		return repo.sumRevenueOnTime(dateStart, dateEnd, statusBill );
	}
	
	@Override
	public List<Booking> getBookingOnTimeByStatusBill(String dateStart, String dateEnd, int statusBill) {
		
		return repo.getBookingOnTimeByStatusBill(dateStart, dateEnd, statusBill);
	}
	
	@Override
	public List<Booking> getAllBookingWithCarOwnerAndStatusBill(int id_user, int statusBill) {
		
		return repo.getAllBookingWithCarOwnerAndStatusBill(id_user, statusBill);
	}
	@Override
	public List<Booking> getAllBookingOnTimeByIdUserHaveCar(String dateStart, String dateEnd, int statusBill,
			int idUser) {
		
		return repo.getAllBookingOnTimeByIdUserHaveCar(dateStart, dateEnd, statusBill, idUser);
	}
	
	@Override
	public String[] sumRevenueOnTimeByIdUser(String dateStart, String dateEnd, int statusBill, int idUser) {
		
		return repo.sumRevenueOnTimeByIdUser(dateStart, dateEnd, statusBill, idUser);
	}

	@Override
	public List<Booking> getBookingByUserIdUserAndStatusBillOrderByDateStartDesc(int idUser, int statusBill) {
		
		return repo.findBookingByUserIdUserAndStatusBillOrderByDateStartDesc(idUser, statusBill);
	}

}
