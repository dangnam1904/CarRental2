package com.project.CarRental2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.CarRental2.model.PaymentBill;
import com.project.CarRental2.repository.PaymentBillRepository;

@Service
public class PaymentBillServiceImpl  implements PaymentBillService{
	
	@Autowired 
	private PaymentBillRepository repository;

	@Override
	public void savePaymentBill(PaymentBill paymentBill) {
	repository.save(paymentBill);
	}

	@Override
	public PaymentBill getAPaymentBill(int idPaymentBill) {
		
		return repository.findById(idPaymentBill).get();
	}

	@Override
	public void deletePaymentBill(int idPaymentBill) {
		repository.deleteById(idPaymentBill);
		
	}

	@Override
	public List<PaymentBill> getAllPaymentBill() {
		return repository.findAll();
	}

}
