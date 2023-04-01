package com.project.CarRental2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.CarRental2.model.District;
import com.project.CarRental2.repository.DistrictRepository;

@Service
public class DistrictServiceImpl implements DistrictService {

	@Autowired
	private DistrictRepository repo;
	
	@Override
	public List<District> getAllDistricWithProvenice() {
		
		return repo.getAllDistrictWithProvince();
	}

	@Override
	public void saveDistrict(District d) {
		repo.save(d);
	}

	@Override
	public District getDistrict(int id) {
		Optional<District> disOptional= repo.findById(id);
		District district= null;
		if(disOptional.isPresent()) {
			district= disOptional.get();
		}else {
			throw new RuntimeException("District not found for id :: " + id);
		}
		return district;
	}

	@Override
	public void deleteDistrict(int id) {
		repo.deleteById(id);
		
	}

	@Override
	public List<District> getAllDistrict() {
		
		return repo.findAll();
	}

	@Override
	public List<District> getAllDistrictByIdProvince(int idProvence) {
		
		return repo.findByProvinceIdProvince(idProvence);
	}

}
