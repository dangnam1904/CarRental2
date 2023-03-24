package com.project.CarRental2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.CarRental2.model.DetailBooking;

@Repository
public interface DetailBookingRepository extends JpaRepository<DetailBooking, Integer> {

}
