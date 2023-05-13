package com.project.CarRental2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.CarRental2.model.RequestWithdrawal;
import com.project.CarRental2.repository.RequestWithdrawalRepository;

@Service
public class RequestWithdrawalServiceImpl  implements RequestWithdrawalService{

	@Autowired
	private RequestWithdrawalRepository repository;
	
	@Override
	public void saveRequestWithdrawal(RequestWithdrawal reqWithdrawal) {
		repository.save(reqWithdrawal);		
	}

	@Override
	public List<RequestWithdrawal> getAllRequestWithdraw() {
		return repository.findAll();
	}

	@Override
	public RequestWithdrawal getRequestWithdrawalById(int idReq) {
		return repository.findById(idReq).get();
	}

	@Override
	public void deleteRequestWithdrawal(int idReq) {
		repository.deleteById(idReq);;
	}

	@Override
	public List<RequestWithdrawal> getAllRequestWithdrawByIdUserOrderByCreateDate(int idUser) {
		
		return repository.getAllWithdrawalByIdUser(idUser);
	}
	@Override
	public List<RequestWithdrawal> getAllRequestWithdrawOrderByCreateDate() {
		return repository.getAllWithdrawal();
	}
	
	@Override
	public void changeStatusRequestWithdraw(int stausRequest, int idRequest) {
		repository.changeStatusRequestPayment(stausRequest, idRequest);
		
	}
}
