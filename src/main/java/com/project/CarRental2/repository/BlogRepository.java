package com.project.CarRental2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.CarRental2.model.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {

	long count();
}
