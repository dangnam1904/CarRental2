package com.project.CarRental2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.CarRental2.model.Car;
@Repository
public interface CarRepository  extends JpaRepository<Car, Integer>{

}
