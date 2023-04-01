package com.project.CarRental2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.CarRental2.model.Blog;
import com.project.CarRental2.service.UploadFileImpl;

@Controller
@RequestMapping("/admin")
public class BlogController {
	
	@GetMapping("/blog/add")
	public String getForm( Model model) {
		model.addAttribute("blog", new Blog());
		return "admin/pages/blogs/add";
	}
	
	@PostMapping("/blog/add")
	public String saveBlogs(@ModelAttribute("blog") Blog blog,
			@RequestParam(name ="image", required = false) MultipartFile img) {
		UploadFileImpl upload= new UploadFileImpl();
		blog.setImageBlog( upload.uploadSingleFile(img));
		System.err.println(blog.toString());
		return "redirect:/admin/blog/add";
	}
}
