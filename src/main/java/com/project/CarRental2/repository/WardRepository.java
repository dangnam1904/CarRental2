package com.project.CarRental2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.CarRental2.model.Ward;

@Repository
public interface WardRepository  extends JpaRepository<Ward, Integer>{

	@Query(value = "select id_ward, w.id_district, w.name_ward, w.create_date, w.update_date, d.name_district"
			+ " from ward w join district d on d.id_district= w.id_district",nativeQuery = true)
	public List<Ward> getAllWardWithDistrict();
	
	@Query(value = "select id_ward, w.id_district, w.name_ward, w.create_date, w.update_date, d.name_district, p.id_province, p.name_province\n"
			+ "from ward w join district d on d.id_district= w.id_district join province  p on p.id_province= d.id_province",nativeQuery = true)
	public List<Ward> getAllWardWithDistrictWithProvinces();
	
	public List<Ward> findWardByDistrictIdDistrict(int idDistrict);
	long count();
}
