package com.project.CarRental2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.CarRental2.model.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query(value =  "select * from users order by username asc", nativeQuery = true)
	List<User> findAllUserOrderbyUsername();
	
	@Transactional
	@Modifying
	@Query(value = "update users set total_money=:totalMoney where id_user=:idUser", nativeQuery = true)
	void updateTotalMoney(@Param("totalMoney") int totalMoney,@Param("idUser") int idUser );

	long count();
	
	Optional<User> findUserByUsername(String username);
	
	@Query(value = "select u.address, u.create_date, u.date_of_brith, u.driving_license, u.email, u.id_role, u.id_user,\n"
			+ "u.image, u.img_driving_license, u.name_display, u.password, u.phone, u.sex,u.total_money, u.update_date, u.username\n"
			+ "from car c join users u on u.id_user= c.id_user where c.id_car=:idCar", nativeQuery = true)
	User getUserByIdCar(@Param("idCar") int idCar);
	
}
