package com.project.CarRental2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.CarRental2.model.User;
import com.project.CarRental2.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService /*, UserDetailsService */ {

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

	@Override
	public void deleteUser(int id) {
		repo.deleteById(id);

	}

	@Override
	public User getAUser(int id_user) {
		Optional<User> optional = repo.findById(id_user);
		User user = null;
		if (optional.isPresent()) {
			user = optional.get();
		}
		return user;
	}

	@Override
	public void updateTotalMoney(int totalMoney, int idUser) {

		 repo.updateTotalMoney(totalMoney, idUser);
	}

	@Override
	public int countUser() {

		return (int) repo.count();
	}


	@Override
	public Optional<User> findUserByUserName(String username) {
		
		return repo.findUserByUsername(username);
	}

	@Override
	public User getUserByIdCar(int idCar) {
		return repo.getUserByIdCar(idCar);
	}
}
