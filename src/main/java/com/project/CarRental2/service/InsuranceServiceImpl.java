package com.project.CarRental2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.CarRental2.model.Insurance;
import com.project.CarRental2.repository.InsuranceRepository;

@Service
public class InsuranceServiceImpl implements InsuranceService {
	
	@Autowired
	private InsuranceRepository repo;

	@Override
	public void saveInsuance(Insurance insurance) {
		repo.save(insurance);
		
	}

	@Override
	public Insurance getInsuranceById(int id) {
		
		return repo.findById(id).get();
	}

	@Override
	public void deleteInsuranceById(int id) {
		repo.deleteById(id);
		
	}

	@Override
	public List<Insurance> getAllInsurance() {
		
		return repo.findAll();
	}

	
}
