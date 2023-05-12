package com.project.CarRental2.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.CarRental2.model.Contract;
import com.project.CarRental2.model.User;
import com.project.CarRental2.service.ContractService;
import com.project.CarRental2.service.UploadFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ContractController {

	@Autowired 
	private ContractService contractService;
	
	@Autowired
	private UploadFile uploadFile;
	
	@GetMapping("/admin/contract")
	public String getAllContract(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				model.addAttribute("listContract", contractService.getAllContract());
				return "admin/pages/contract/list";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	
	}
	@GetMapping("/admin/contract/add")
	public String getFormContract(Model model,  HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				model.addAttribute("contract", new Contract());
				return "admin/pages/contract/add";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}
	
	@PostMapping("/admin/contract/add")
	public String saveContract(Model model, @ModelAttribute("contract") Contract contract,
			@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				contract.setCreateDate(new Date());
				contract.setUpdateDate(new Date());
				contract.setNameFile( uploadFile.uploadFileDocument(file));
				contractService.saveContract(contract);
				return "redirect:/admin/contract";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}
	
	@GetMapping("/admin/contract/edit/{idContract}")
	public String getFormEditContract(Model model, @PathVariable("idContract") int idContract,
			 HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				model.addAttribute("contract", contractService.getContractById(idContract));
				return "admin/pages/contract/edit";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
		
	}
	
	@PostMapping("/admin/contract/edit")
	public String saveEditContract(Model model, @ModelAttribute("contract") Contract contract,
			@RequestParam("file") MultipartFile file,  HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if(sessionUser!=null) {
			if (sessionUser.getRole().getNameRole().equals("Admin")) {
				Contract oldContract= contractService.getContractById(contract.getIdContract());
				contract.setCreateDate(oldContract.getCreateDate());
				contract.setUpdateDate(new Date());
				if(file.getOriginalFilename()=="") {
					contract.setNameFile(oldContract.getNameFile());
				}else {
					uploadFile.removeFile(oldContract.getNameFile());
					contract.setNameFile( uploadFile.uploadFileDocument(file));
				}
				contractService.saveContract(contract);
				return "redirect:/admin/contract";
			}else {
				return "redirect:/login";
			}
		}
		 else {
			 return "redirect:/login";
		}
	}
	
	
}
