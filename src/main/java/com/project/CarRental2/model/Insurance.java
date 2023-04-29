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
@NoArgsConstructor
@AllArgsConstructor
public class Insurance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idInsurance;
	@Column(columnDefinition = "nvarchar(50)")
	private String nameInsurance;
	
	@Column(columnDefinition = "nvarchar(150)")
	private String imageInsurance;
	
	@Column(columnDefinition = "ntext")
	private String contentInsurance;
	private Date createDate;
	private Date updateDate;
}
