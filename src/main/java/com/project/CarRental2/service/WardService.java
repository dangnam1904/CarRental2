package com.project.CarRental2.service;

import java.util.List;

import com.project.CarRental2.model.Ward;

public interface WardService {

	List<Ward> getAllWardWithDistrictWithProvinces();
	List<Ward> getAllWardWithDistrict();
	List<Ward> getAllWard();
	Ward getAWard(int id);
	void saveWard(Ward ward);
	void deleteWard(int id);
	List<Ward> getAllWardByIdDistric(int idDistrict);
	
 	
}
