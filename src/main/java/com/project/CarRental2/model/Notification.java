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
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idNoti;
	
	@Column(columnDefinition = "nvarchar(200)")
	private String image;
	
	@Column(columnDefinition = "nvarchar(500)")
	private String titleNoti;
	
	@Column(columnDefinition = "nvarchar(2000)")
	private String contentNoti;

	private Date createDate;
	private Date updateDate;

	@OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	private List<DetailNotification> detailNotifications;

	public Notification(int idNoti, String image, String titleNoti, String contentNoti, Date createDate,
			Date updateDate) {
		this.idNoti = idNoti;
		this.image = image;
		this.titleNoti = titleNoti;
		this.contentNoti = contentNoti;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
	
}
