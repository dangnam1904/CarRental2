package com.project.CarRental2.service;

import java.util.List;

import com.project.CarRental2.model.Car;

public interface CarService {

	void saveCar(Car car);
	List<Car> getAllCarOrderByNameCarAsc();
	Car getACarByIdCar(int id_car);
	void deleteACarById(int id_car);
	void changeStatusCar(int status, int id_car);
	List<Car> getAllCarWithUserAndBrandOrderByNameCarAsc();
	List<Car> getAllCarByIdUser(int id_user);
	int getSumCarInAddress(String address);
	int getSumCarInAddressHasDriver(String address, boolean driver);
	List<Car> getAllCarByDriverOderByName(boolean driver);
	List<Car> getAllCarByDriverInAddressOrderByName(boolean driver,String address);
	List<Car> getAllCarByDriverInAddressAndPromotionalPriceOderByName(boolean driver,String address);
	List<Car> findCarOnTimeByDriverAndAddress(boolean driver, String address, String dateStart, String dateEnd, int status);
	int countCar();
	List<Car> getAllCarByDriverAndStatusCarOderByName(boolean driver, int status);
	List<Car> findCarByUserIdUserAndStatusOrderByNameCar(int id_user, int status);
	void updatePromotionalPriceCar( int promotionalPrice);
	List<Car> findCarByNameCarContaining(String nameCar);
	void resetPromotionalPriceCar(int oldPromotionalPrice, int idCar);
}
