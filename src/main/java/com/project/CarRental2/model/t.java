package com.project.CarRental2.model;

public class t {
	
	public static void main(String[] args) {
		int thuong =0;
		try {
			thuong= 10/0;
		}catch(Exception e) {
			System.err.println(e);
			thuong=0;
		}
		System.out.println(thuong);
	}

}
