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
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Contract {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idContract;
	@Column(columnDefinition = "nvarchar(200) not null")
	private String  titleContract;
	@Column(columnDefinition = "nvarchar(200) not null")
	private String nameFile;
	private Date createDate;
	private Date updateDate;
}
