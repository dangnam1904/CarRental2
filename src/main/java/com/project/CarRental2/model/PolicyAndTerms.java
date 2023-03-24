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
public class PolicyAndTerms {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPolicy;
	@Column(columnDefinition = "nvarchar(700) not null")
	private String titlePolicy;
	@Column(columnDefinition = "ntext")
	private String contentPolicy;
	private Date createDate;
	private Date updateDate;
}
