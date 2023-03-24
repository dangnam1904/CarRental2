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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
	private String image;
	@Column(columnDefinition = "nvarchar(2000)")
	private String contentNoti;
	
	private Date createDate;
	private Date updateDate;
	
	 @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    // Quan hệ n-n với đối tượng ở dưới (Notication) (1 thông báo có nhiều người có)
	    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
	    @ToString.Exclude // Khoonhg sử dụng trong toString()

	    @JoinTable(name = "detail_notification",

	            joinColumns = @JoinColumn(name = "id_noti"),
	            inverseJoinColumns = @JoinColumn(name = "id_user")
	    )
	    private List<User> users;
}
