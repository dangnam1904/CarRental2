package com.project.CarRental2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.CarRental2.model.Car;
import com.project.CarRental2.repository.CarRepository;

@Service
public class CarServiceImpl implements CarService {

	@Autowired
	private CarRepository repository;
	@Override
	public void saveCar(Car car) {
		repository.save(car);
		
	}
	@Override
	public List<Car> getAllCarOrderByNameCarAsc() {
		
		return repository.getAllCarOrderByNameCarAsc();
	}
	@Override
	public Car getACarByIdCar(int id_car) {
		Optional< Car> optional= repository.findById(id_car);
		Car car= null;
		if(optional.isPresent()) {
			car= optional.get();
		
		}
		return car;
	}
	@Override
	public void deleteACarById(int id_car) {
		repository.deleteById(id_car);
		
	}
	@Override
	public void changeStatusCar(int status, int id_car) {
		repository.changeStatusCar(status, id_car);
		
	}
	@Override
	public List<Car> getAllCarWithUserAndBrandOrderByNameCarAsc() {
		
		return repository.getAllCarWithUserAndBrandOrderByNameCarAsc();
	}
	@Override
	public List<Car> getAllCarByIdUser(int id_user) {
		
		return repository.findCarByUserIdUserOrderByNameCarAsc(id_user);
	}
	@Override
	public int getSumCarInAddress(String address) {
		
		return repository.countByAddressCarContaining(address);
	}
	@Override
	public int getSumCarInAddressHasDriver(String address,boolean driver) {
		
		return repository.countByAddressCarContainingAndDriver(address, driver);
	}
	@Override
	public List<Car> getAllCarByDriverOderByName(boolean driver) {
		return repository.getAllCarByDriverOderByName(driver);
	}
	@Override
	public List<Car> getAllCarByDriverInAddressOrderByName(boolean driver, String address) {
		
		return repository.getAllCarByDriverInAddressOderByName(driver, address);
	}
	@Override
	public List<Car> getAllCarByDriverInAddressAndPromotionalPriceOderByName(boolean driver, String address) {
		
		return repository.getAllCarByDriverInAddressAndPromotionalPriceOderByName(driver, address);
	}
	@Override
	public List<Car> findCarOnTimeByDriverAndAddress(boolean driver, String address, String dateStart, String dateEnd, int status) {
		
		return repository.findCarOnTimeByDriverAndAddress(driver, address, dateStart, dateEnd, status);
	}

	@Override
	public int countCar() {
		
		return (int) repository.count();
	}
	@Override
	public List<Car> getAllCarByDriverAndStatusCarOderByName(boolean driver, int status) {
		return repository.getAllCarByDriverAndStatusCarOderByName(driver, status);
	}
	@Override
	public List<Car> findCarByUserIdUserAndStatusOrderByNameCar(int id_user, int status) {
	
		return repository.findCarByUserIdUserAndStatusOrderByNameCar(id_user, status);
	}
	
	@Override
	public void updatePromotionalPriceCar(int promotionalPrice) {
		repository.updatePromotionalPriceCar(promotionalPrice);
	}
	
	@Override
	public List<Car> findCarByNameCarContaining(String nameCar) {
		
		return repository.findCarByNameCarContaining(nameCar);
	}

	@Override
	public void resetPromotionalPriceCar(int oldPromotionalPrice, int idCar) {
		repository.resetPromotionalPriceCar(oldPromotionalPrice, idCar);
	}
}
