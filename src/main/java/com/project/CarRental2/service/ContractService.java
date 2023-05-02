package com.project.CarRental2.service;

import java.util.List;

import com.project.CarRental2.model.Contract;

public interface ContractService {
	List<Contract> getAllContract();
	Contract getContractById(int idContract);
	void saveContract(Contract contract);
	void deleteContract(int idContract);
}
