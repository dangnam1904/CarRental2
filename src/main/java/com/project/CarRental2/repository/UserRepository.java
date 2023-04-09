package com.project.CarRental2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.CarRental2.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query(value =  "select * from users order by username asc", nativeQuery = true)
	List<User> findAllUserOrderbyUsername();
	

}
