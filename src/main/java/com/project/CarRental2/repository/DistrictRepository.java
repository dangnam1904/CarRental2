package com.project.CarRental2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.CarRental2.model.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
	@Query(value = "select id_district,name_district,d.create_date, d.update_date, p.id_province, p.name_province  "
			+ " from district d join province p on p.id_province= d.id_province", nativeQuery = true)
	List<District> getAllDistrictWithProvince();
	List<District> findByProvinceIdProvince(int idProvince);
	long count();
}
