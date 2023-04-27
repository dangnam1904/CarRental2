package com.project.CarRental2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.CarRental2.model.Blog;
import com.project.CarRental2.repository.BlogRepository;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogRepository repo;

	@Override
	public List<Blog> getAllBlog() {
		return repo.findAll();
	}

	@Override
	public Blog getBlogById(int id) {
		Optional<Blog> optionBlog = repo.findById(id);
		Blog blog = null;
		if (optionBlog.isPresent()) {
			blog = optionBlog.get();
		}else {
			throw new RuntimeException("Blog hasn't id : "+id+"");
		}
		return blog;
	}

	@Override
	public void saveBlog(Blog blog) {
		repo.save(blog);
	}

	@Override
	public void deleteBlog(int id) {
		repo.deleteById(id);
	}

	@Override
	public int countBlog() {
		
		return (int) repo.count();
	}

}
