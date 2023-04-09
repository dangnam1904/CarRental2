package com.project.CarRental2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.CarRental2.model.BrandCar;
import com.project.CarRental2.repository.BrandCarRepository;

@Service
public class BrandCarServiceImpl  implements BrandCarService{

	@Autowired
	private BrandCarRepository repo;
	@Override
	public BrandCar getBrandCarById(int id) {
		Optional<BrandCar> optional= repo.findById(id);
		BrandCar brandCar= null;
		if(optional.isPresent()) {
			brandCar= optional.get();
		}else {
			//throw new RuntimeException("BranhCar not exsist id"+id+"");
		}
		return brandCar;
	}

	@Override
	public List<BrandCar> getAllBrachCar() {
		
		return repo.findAll();
	}

	@Override
	public void deleteBrandCar(int id) {
		repo.deleteById(id);
		
	}

	@Override
	public void saveBrandCar(BrandCar brandCar) {
		repo.save(brandCar);
		
	}

	@Override
	public boolean checkNameBranchExist(List<BrandCar> listBrandCar, BrandCar branchCar) {
		for (BrandCar brandCar1 : listBrandCar) {
			if(brandCar1.getNameBrand().equals(branchCar.getNameBrand())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<BrandCar> getAllBrandCarOderByNameAsc() {
		
		return repo.getAllBrandCarOderByNameAsc();
	}


}
