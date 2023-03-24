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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCar;
	@Column(columnDefinition = "nvarchar(50) not null")
	private String nameCar;
	@Column(columnDefinition = "nvarchar(4000)")
	private String overviewCar;
	private int price;
	@Column(columnDefinition = "nvarchar(20)")
	private String licensePlates;
	private int modelYear;
	private int satatus;
	private int numberOfSeats;
	@Column(columnDefinition = "nvarchar(2000)")
	private String imageCar;
	private boolean airConditioner;
	private boolean antiLockBrakingSystem;
	private boolean maps;
	private boolean journeyCamera;
	private boolean reverseCamera;
	private boolean spareTire;
	private boolean camera360;
	private boolean ollisionsensor;
	private boolean speedWarning;
	private boolean gpsLocator;
	private boolean sunroof;
	private boolean dvdScreen;
	private boolean driver;
	private boolean manualTransmissionCar;
	private Date createDate;
	private Date updateDate;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JoinColumn(name= "idCar", referencedColumnName = "idCar")
	private List<DetailBooking> detailBookings;

}
