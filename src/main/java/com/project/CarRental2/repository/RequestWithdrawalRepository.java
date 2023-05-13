package com.project.CarRental2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.CarRental2.model.RequestWithdrawal;

import jakarta.transaction.Transactional;

@Repository
public interface RequestWithdrawalRepository extends JpaRepository<RequestWithdrawal, Integer> {

	@Modifying
	@Transactional
	@Query(value = "select * from request_withdrawal order by create_date desc", nativeQuery = true)
	List<RequestWithdrawal> getAllWithdrawal();
	
	@Modifying
	@Transactional
	@Query(value = "select * from request_withdrawal where id_user=:idUser order by create_date desc", nativeQuery = true)
	List<RequestWithdrawal> getAllWithdrawalByIdUser(@Param("idUser") int idUser);
	
	@Modifying
	@Transactional
	@Query(value = "update request_withdrawal set  status_request=:stausRequest  where id_request=:idRequest",nativeQuery = true)
	void changeStatusRequestPayment(@Param("stausRequest") int stausRequest, @Param("idRequest") int idRequest);
}
