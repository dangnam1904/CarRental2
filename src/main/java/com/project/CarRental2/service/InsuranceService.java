package com.project.CarRental2.service;

import java.util.List;

import com.project.CarRental2.model.Insurance;

public interface InsuranceService {
	void saveInsuance(Insurance insurance);
	Insurance getInsuranceById(int id);
	void deleteInsuranceById(int id);
	List<Insurance> getAllInsurance();
}
