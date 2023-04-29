package com.project.CarRental2.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
	public boolean updateTotalMoney(int totalMoney, int idUser) {

		return repo.updateTotalMoney(totalMoney, idUser);
	}

	@Override
	public int countUser() {

		return (int) repo.count();
	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<User> option = repo.findUserByUsername(username);
//		
//		org.springframework.security.core.userdetails.User springUser=null;
//		if(option.isEmpty()) {
//			throw new UsernameNotFoundException("User with username: " +username +" not found");
//		}
//			User user= option.get();
//			String role =  user.getRole().getNameRole();
//			Set<GrantedAuthority> ga = new HashSet<>();
//				ga.add(new SimpleGrantedAuthority(role));
//			springUser = new org.springframework.security.core.userdetails.User(
//							username,
//							user.getPassword(),
//							ga );	
//		return springUser;
//	}

	@Override
	public Optional<User> findUserByUserName(String username) {
		
		return repo.findUserByUsername(username);
	}

}
