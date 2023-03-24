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
public class Wards {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idWards;
	@Column(columnDefinition = "nvarchar(150) not null")
	private String nameWards;
	private Date createDate;
	private Date updateDate;
	

}
