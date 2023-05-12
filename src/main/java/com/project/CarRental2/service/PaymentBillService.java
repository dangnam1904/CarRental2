package com.project.CarRental2.service;

import java.util.List;

import com.project.CarRental2.model.PaymentBill;

public interface PaymentBillService {

	void savePaymentBill(PaymentBill paymentBill);
	PaymentBill getAPaymentBill(int idPaymentBill);
	void deletePaymentBill(int idPaymentBill);
	List<PaymentBill> getAllPaymentBill();
}
