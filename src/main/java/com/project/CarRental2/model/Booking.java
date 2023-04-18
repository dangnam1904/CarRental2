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
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idBooking;
	@Column(columnDefinition = "nvarchar(12)")
	private String phone;
	@Column(columnDefinition = "nvarchar(300)")
	private String address;
	
	private int statusBill;
	private Date dateStart;
	private Date dateEnd;
	private int billTotal;
	
	private Date createDate;
	private Date updateDate;

	// mapped by is mapping name class
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_car")
	@JsonIgnore
	private Car car;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user")
	@JsonIgnore
	private User user;
}
