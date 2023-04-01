package com.project.CarRental2.service;

import java.util.List;

import com.project.CarRental2.model.Province;

public interface ProvinceService {
	
	List<Province> getAllProvinceOrderByName();
	void saveProvices(Province p);
	Province getProvince(int id);
	void deleteProvince(int id);

}
