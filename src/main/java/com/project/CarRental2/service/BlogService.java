package com.project.CarRental2.service;

import java.util.List;

import com.project.CarRental2.model.Blog;

public interface BlogService {
	List<Blog> getAllBlog();

	Blog getBlogById(int id);

	void saveBlog(Blog blog);

	void deleteBlog(int id);
	int countBlog();
}
