package com.project.CarRental2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.CarRental2.model.BrandCar;

@Repository
public interface BrandCarRepository extends JpaRepository<BrandCar, Integer> {
	@Query(value="select * from brand_car order by name_brand asc", nativeQuery = true)
	List<BrandCar> getAllBrandCarOderByNameAsc();
	long count();

}
