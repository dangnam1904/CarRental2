package com.project.CarRental2.model;

import java.util.Date;

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
public class Ward {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idWard;
	@Column(columnDefinition = "nvarchar(150) not null")
	private String nameWard;
	private Date createDate;
	private Date updateDate;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_district")
	private District district;
	public Ward(int idWard, String nameWard) {
		
		this.idWard = idWard;
		this.nameWard = nameWard;
	}
	

}
