package com.project.CarRental2.model;

import java.util.Date;

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

@Entity
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestWithdrawal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idRequest;
	
	@Column(columnDefinition = "nvarchar(500)")
	private String nameBank;
	
	@Column(columnDefinition = "nvarchar(20)")
	private String accountNumber;
	
	@Column(columnDefinition = "nvarchar(60)")
	private String nameAccount;
	private int moneyNumber;
	
	private int statusRequest;
	private Date createDate;
	private Date updateDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user")
	@JsonIgnore
	private User user;
}
