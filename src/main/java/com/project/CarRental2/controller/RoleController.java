package com.project.CarRental2.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.CarRental2.model.Role;
import com.project.CarRental2.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	// @GetMapping("/role")
	// public List<Role> getAllRole(){
	// return roleService.getAllRole();
	// }

	@GetMapping("")
	public ResponseEntity<List<Role>> getAllRoles() {
		roleService.getAllRole();
		return ResponseEntity.ok(roleService.getAllRole());
	}

	@PostMapping("")
	public ResponseEntity<Role> addRole(@RequestBody Role role) {

		role.setCreateDate(new Date());
		roleService.saveRole(role);
		System.out.println(role);
		return ResponseEntity.ok(role);
	}

	@GetMapping("/hello")
	public String getString() {

		return "Hello vuejs";
	}
}
