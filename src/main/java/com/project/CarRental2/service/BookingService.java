package com.project.CarRental2.service;

import java.util.Date;
import java.util.List;

import com.project.CarRental2.model.Booking;

public interface BookingService {
	void saveBooking(Booking booking);
	Booking getABooking(int idBooking);
	void deleteBooking(int idBooking);
	List<Booking> getAllBooking();
	void  changeStatusBill(int satatuBill, int idBooking);
	List<Booking> getAllBookingWithIdUserLessee(int idBooking);
	List<Booking> getAllBookingWithCarOwner(int idUser);
	List<Booking> checkItemInTime(int idCar, Date dateStart, Date dateEnd);
	List<Booking> checkBillExistOnTime(int idCar, String dateStart, String dateEnd);
	List<Booking> getAllBookingOnTime(String dateStart, String dateEnd);
	int countDeliveryByIdCar(int idCar);
	int countBill();
	List<Booking> getAllBookingByStatusBill(int statusBill);
	String[] sumRevenueOnTime(String dateStart, String dateEnd, int statusBill);
	String[] sumRevenueOnTimeByIdUser(String dateStart, String dateEnd, int statusBill, int idUser);
	List<Booking> getBookingOnTimeByStatusBill(String dateStart, String dateEnd, int statusBill);
	List<Booking> getAllBookingWithCarOwnerAndStatusBill(int id_user, int statusBill);
	List<Booking> getAllBookingOnTimeByIdUserHaveCar(String dateStart, String dateEnd, int statusBill, int idUser);
	List<Booking> getBookingByUserIdUserAndStatusBillOrderByDateStartDesc(int idUser, int statusBill);
}
