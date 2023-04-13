package com.project.CarRental2.service;

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
	public List<Booking> getAllBookingWithIdUser(int idUser) {
		return repo.findBookingByUserIdUserOrderByDateStart(idUser);
	}

	@Override
	public List<Booking> getAllBookingWithCarOwner(int idUser) {
		
		return repo.getAllBookingWithCarOwner(idUser);
	}

}
