package com.project.CarRental2.service;

import java.util.List;

import com.project.CarRental2.model.Booking;

public interface BookingService {
	void saveBooking(Booking booking);
	Booking getABooking(int idBooking);
	void deleteBooking(int idBooking);
	List<Booking> getAllBooking();
	void  changeStatusBill(int satatuBill, int idBooking);
	List<Booking> getAllBookingWithIdUser(int idBooking);
	List<Booking> getAllBookingWithCarOwner(int idUser);
}
