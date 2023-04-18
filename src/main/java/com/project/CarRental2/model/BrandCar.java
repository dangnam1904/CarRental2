package com.project.CarRental2.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BrandCar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idBrand;
	@Column(columnDefinition = "nvarchar(50) not null")
	private String nameBrand;
	private Date createDate;
	private Date updateDate;
	
	// mapped by is mapping name class
		@OneToMany(mappedBy = "brandCar" ,cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
		@EqualsAndHashCode.Exclude
		@ToString.Exclude
		@JsonIgnore
		private List<Car> cars;
		// mapping by will map variable in class car, 
		
}
