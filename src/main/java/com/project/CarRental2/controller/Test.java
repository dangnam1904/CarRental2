package com.project.CarRental2.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	public static void main(String[] args) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
		String a="05-08";
		System.err.println(a.compareTo(dateFormat.format(now)));
	}

}
