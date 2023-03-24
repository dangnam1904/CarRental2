package com.project.CarRental2.model;

import java.util.Date;

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
public class DetailBooking {
@Id
@GeneratedValue( strategy = GenerationType.IDENTITY)
private int idDetailBook;

private Date dateStart;
private Date dateEnd;
private int billTotal;

}
