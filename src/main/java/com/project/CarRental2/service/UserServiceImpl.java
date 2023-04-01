package com.project.CarRental2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.CarRental2.model.User;
import com.project.CarRental2.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repo;
	
	@Override
	public void saveUser(User u) {
		repo.save(u);
	}

	@Override
	public List<User> getAllUser() {
		
		return repo.findAll();
	}

	@Override
	public List<User> getAllUserOrderByUsername() {
		
		return repo.findAllUserOrderbyUsername();
	}

}
