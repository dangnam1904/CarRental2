package com.project.CarRental2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.CarRental2.model.Ward;
import com.project.CarRental2.repository.WardRepository;

@Service
public class WardServiceImpl  implements WardService{

	@Autowired
	private WardRepository repo;
	@Override
	public List<Ward> getAllWardWithDistrict() {
		
		return repo.getAllWardWithDistrict();
	}

	@Override
	public List<Ward> getAllWard() {
	
		return repo.findAll();
	}

	@Override
	public Ward getAWard(int id) {
		Optional<Ward> optionWard= repo.findById(id);
		Ward ward= null;
		if(optionWard.isPresent()) {
			ward= optionWard.get();
		}else {
			throw new RuntimeException("Ward not found for id :: " + id);
		}
		return ward;
	}

	@Override
	public void saveWard(Ward ward) {
		repo.save(ward);
	}

	@Override
	public void deleteWard(int id) {
		repo.deleteById(id);
	}

	@Override
	public List<Ward> getAllWardWithDistrictWithProvinces() {
		
		return repo.getAllWardWithDistrictWithProvinces();
	}

	@Override
	public List<Ward> getAllWardByIdDistric(int idDistrict) {
		
		return repo.findWardByDistrictIdDistrict(idDistrict);
	}

	
}
