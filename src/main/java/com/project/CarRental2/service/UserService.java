package com.project.CarRental2.service;

import java.util.List;

import com.project.CarRental2.model.User;

public interface UserService {

	void saveUser( User u);
	List<User> getAllUser();
	List<User> getAllUserOrderByUsername();
	
}
