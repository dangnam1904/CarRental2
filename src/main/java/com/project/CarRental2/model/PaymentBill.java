package com.project.CarRental2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PaymentBill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPayment;

	@Column(columnDefinition = "nvarchar(50) not null")
	private String nameBank;
	
	@Column(columnDefinition = "nvarchar(50) not null")
	private String bankTranNo;
	
	@Column(columnDefinition = "nvarchar(50) not null")
	private String responseCode;
	private int amountBill;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user")
	@JsonIgnore
	private User user;
	
}
