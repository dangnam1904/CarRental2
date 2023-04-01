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
public class Blog {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private int idBlog;
	
	@Column(columnDefinition = "nvarchar(400) not null")
	private String titleBlog;
	
	@Column(columnDefinition = "nvarchar(1000) not null")
	private String describeBlog;
	
	@Column(columnDefinition = "nvarchar(100)")
	private String imageBlog;
	
	@Column( columnDefinition = "ntext")
	private String contentBlog;
	private Date createDate;
	private Date updateDate;
}
