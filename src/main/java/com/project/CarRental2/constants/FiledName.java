package com.project.CarRental2.constants;

public interface FiledName {
	public static final int ROLE_ADMIN = 1;
	public static final int ROLE_USER = 2;
	public static final int ROLE_CUSTOMMER_CARE = 3;
	public static final int ROLE_WRITE_CONTENT = 4;
	public static final int ROLE_ACCOUNTANT = 5;
	
	public static final int STATUS_PENDING = 0;
	public static final int STATUS_APPROVED = 1;
	public static final int STATUS_CANCAL = 3;
	public static final int STATUS_PAYMENT = 2;
	public static final int ALL_STATUS = 5;
	public static final int STATUS_STOP = 4;
	
	public static final boolean HAS_DRIVERS = true;
	public static final boolean NO_DRIVERS = false;
	
	public static final int NO_READING = 0;
	public static final int READING=1;
	
	public static final double SYSTEM_DISCOUNT=0.05;
}
