package com.project.CarRental2.service;

import java.util.List;

import com.project.CarRental2.model.BrandCar;

public interface BrandCarService {
	
	BrandCar getBrandCarById(int id);
	List<BrandCar> getAllBrachCar();
	void deleteBrandCar(int id);
	void saveBrandCar(BrandCar brandCar);
	boolean checkNameBranchExist(List<BrandCar> listBrandCar, BrandCar branchCar);
	List<BrandCar> getAllBrandCarOderByNameAsc();

}
