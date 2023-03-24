package com.project.CarRental2.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class District {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idDistrict;
	@Column(columnDefinition = "nvarchar(60) not null")
	private String nameDistrict;
	private Date createDate;
	private Date updateDate;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JoinColumn(name= "idDistrict", referencedColumnName = "idDistrict")
	private List<Wards> wards;
}
