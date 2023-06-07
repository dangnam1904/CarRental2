package com.project.CarRental2.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.CarRental2.constants.FiledName;
import com.project.CarRental2.model.Blog;
import com.project.CarRental2.model.User;
import com.project.CarRental2.service.BlogService;
import com.project.CarRental2.service.EncryptionPassword;
import com.project.CarRental2.service.UploadFileImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController implements FiledName {
	@Autowired
	private BlogService blogService;

	@GetMapping("/blog/add")
	public String getForm(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if (sessionUser != null) {
			if (sessionUser.getRole().getIdRole() == ROLE_WRITE_CONTENT 
					|| sessionUser.getRole().getIdRole() == ROLE_ADMIN ) {
				model.addAttribute("blog", new Blog());
				return "admin/pages/blogs/add";
			} else {
				return "redirect:/login";
			}
		} else {
			return "redirect:/login";
		}

	}

	@GetMapping("/blog")
	public String getAll(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if (sessionUser != null) {
			if (sessionUser.getRole().getIdRole() == ROLE_WRITE_CONTENT 
					|| sessionUser.getRole().getIdRole() == ROLE_ADMIN ) {
				model.addAttribute("blogs", blogService.getAllBlog());
				return "admin/pages/blogs/list";
			} else {
				return "redirect:/login";
			}
		} else {
			return "redirect:/login";
		}

	}

	@PostMapping("/blog/add")
	public String saveBlogs(@ModelAttribute("blog") Blog blog,
			@RequestParam(name = "image", required = false) MultipartFile img, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if (sessionUser != null) {
			if (sessionUser.getRole().getIdRole() == ROLE_WRITE_CONTENT 
					|| sessionUser.getRole().getIdRole() == ROLE_ADMIN ) {
				UploadFileImpl upload = new UploadFileImpl();
				blog.setImageBlog(upload.uploadSingleFile(img));
				blog.setCreateDate(new Date());
				blog.setUpdateDate(new Date());
				System.err.println(blog.toString());
				blogService.saveBlog(blog);
				return "redirect:/admin/blog";
			} else {
				return "redirect:/login";
			}
		} else {
			return "redirect:/login";
		}
	}

	@GetMapping("/blog/edit/{id}")
	public String editBlog(Model model, @PathVariable(name = "id") int idBlog, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if (sessionUser != null) {
			if (sessionUser.getRole().getIdRole() == ROLE_WRITE_CONTENT 
					|| sessionUser.getRole().getIdRole() == ROLE_ADMIN ) {
				model.addAttribute("blog", blogService.getBlogById(idBlog));
				return "admin/pages/blogs/edit";
			} else {
				return "redirect:/login";
			}
		} else {
			return "redirect:/login";
		}
	}

	@GetMapping("/blog/delete/{id}")
	public String deleteBlog(Model model, @PathVariable(name = "id") int idBlog, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if (sessionUser != null) {
			if (sessionUser.getRole().getIdRole() == ROLE_WRITE_CONTENT 
					|| sessionUser.getRole().getIdRole() == ROLE_ADMIN ) {
				blogService.deleteBlog(idBlog);
				return "redirect:/admin/blog";
			} else {
				return "redirect:/login";
			}
		} else {
			return "redirect:/login";
		}

	}

	@PostMapping("/blog/edit")
	public String saveEditBlogs(@ModelAttribute("blog") Blog blog,
			@RequestParam(name = "image", required = false) MultipartFile img, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("sesionUser");
		if (sessionUser != null) {
			if (sessionUser.getRole().getIdRole() == ROLE_WRITE_CONTENT 
					|| sessionUser.getRole().getIdRole() == ROLE_ADMIN ){
				Blog oldBlog = blogService.getBlogById(blog.getIdBlog());
				UploadFileImpl upload = new UploadFileImpl();
				if (img.getOriginalFilename() == "") {

					blog.setImageBlog(oldBlog.getImageBlog());
				} else {
					upload.removeFile(oldBlog.getImageBlog());
					blog.setImageBlog(upload.uploadSingleFile(img));
				}

				blog.setCreateDate(oldBlog.getCreateDate());
				blog.setUpdateDate(new Date());
				System.err.println(blog.toString());
				blogService.saveBlog(blog);
				return "redirect:/admin/blog";
			} else {
				return "redirect:/login";
			}
		} else {
			return "redirect:/login";
		}

	}

}
