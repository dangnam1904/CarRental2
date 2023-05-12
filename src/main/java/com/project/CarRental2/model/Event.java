package com.project.CarRental2.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEvent;
	
	@Column(columnDefinition = "nvarchar(12)")
	private String dateStartEvent;
	
	@Column(columnDefinition = "nvarchar(12)")
	private String dateEndEvent;
	
	@Column(columnDefinition = "nvarchar(500)")
	private String titleEvent;
	
	@Column(columnDefinition = "nvarchar(500)")
	private String contentEvent;
	
	private int discount;
	
	private Date createDate;
	private Date updateDate;
}
