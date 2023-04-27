package com.project.CarRental2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.CarRental2.model.PolicyAndTerms;
@Repository
public interface PolicyAndTermsRepository  extends JpaRepository<PolicyAndTerms, Integer>{
	long count();
}
