package com.project.CarRental2.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.CarRental2.model.Role;
import com.project.CarRental2.model.User;
import com.project.CarRental2.service.RoleService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@GetMapping("/admin/role")
	public String getAllRole(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				model.addAttribute("listRole", roleService.getAllRole());
				return "admin/pages/role/list";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}
	
	@GetMapping("/admin/role/add")
	public String getFormRole(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				model.addAttribute("role", new Role());
				return "admin/pages/role/form";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}
	
	@GetMapping("/admin/role/save")
	public String saveRole(Model model, @ModelAttribute("role") Role role,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				if(role.getIdRole()!=0) {
					Role oldRole= roleService.getRoleById(role.getIdRole());
					role.setCreateDate(oldRole.getCreateDate());
					role.setUpdateDate(new Date());
					roleService.saveRole(role);
				}else {
					role.setCreateDate(new Date());
					role.setUpdateDate(new Date());
					roleService.saveRole(role);
				}
				return "redirect:/admin/role";
				
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}

	@GetMapping("/admin/role/edit/{idRole}")
	public String editRole(Model model, @PathVariable("idRole") int idRole,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				model.addAttribute("role", roleService.getRoleById(idRole));
				return "admin/pages/role/form";
				
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}
	
	@GetMapping("/admin/role/delete/{idRole}")
	public String deleteRole(Model model, @PathVariable("idRole") int idRole,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				roleService.deteleRole(idRole);
				return "redirect:admin/role";
				
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}
	// @GetMapping("/role")
	// public List<Role> getAllRole(){
	// return roleService.getAllRole();
	// }

//	@GetMapping("")
//	public ResponseEntity<List<Role>> getAllRoles() {
//		roleService.getAllRole();
//		return ResponseEntity.ok(roleService.getAllRole());
//	}
//
//	@PostMapping("")
//	public ResponseEntity<Role> addRole(@RequestBody Role role) {
//
//		role.setCreateDate(new Date());
//		roleService.saveRole(role);
//		System.out.println(role);
//		return ResponseEntity.ok(role);
//	}

	@GetMapping("/hello")
	public String getString() {

		return "Hello vuejs";
	}
}
