package com.project.CarRental2.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResuftPayment implements Serializable {

	private static final long serialVersionUID = 1L;
	private String status;
	private String message;
	private String url;
}
