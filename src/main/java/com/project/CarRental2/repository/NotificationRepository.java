package com.project.CarRental2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.CarRental2.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
	long count();
	
	@Query(value = "SELECT TOP 1 * FROM NOTIFICATION ORDER BY ID_NOTI DESC", nativeQuery = true)
	Notification getNotificationLaster();
	
	@Query(value = "select n.id_noti, n.content_noti, n.create_date, n.image, n.title_noti,n.update_date, dt.id_detail_noti, dt.id_user \n"
			+ "from notification n join detail_notification dt on dt.id_noti= n.id_noti join users u on u.id_user= dt.id_user\n"
			+ "where u.id_user=:idUser", nativeQuery = true)
	List<Notification> getNotificationByIdUser(@Param("idUser") int idUser);
}
