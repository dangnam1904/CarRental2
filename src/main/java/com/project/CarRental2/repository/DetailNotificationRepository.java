package com.project.CarRental2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.CarRental2.model.DetailNotification;

import jakarta.transaction.Transactional;

@Repository
public interface DetailNotificationRepository  extends JpaRepository<DetailNotification, Integer>{

	@Transactional
	@Modifying
	@Query(value = "select  dt.id_detail_noti, dt.url, dt.status_read, dt.id_user, dt.id_noti \r\n"
			+ "from notification n join detail_notification dt on dt.id_noti= n.id_noti join users u on u.id_user= dt.id_user\r\n"
			+ "where u.id_user=:idUser  order by n.create_date desc", nativeQuery = true)
	List<DetailNotification> getDetailNotification(@Param("idUser") int idUser);
	
	@Modifying
	@Transactional
	@Query(value = "update detail_notification set status_read=:status where id_user=:id_user", nativeQuery = true)
	void updateStatusAllByIDUserNotification(@Param("status") int status, @Param("id_user") int id_user);
	
	@Modifying
	@Transactional
	@Query(value = "update detail_notification set status_read=:status where id_user=:id_user and id_noti=:id_noti", nativeQuery = true)
	void updateStatusNotificationByIDNotiAndIDUser(@Param("status") int status, @Param("id_user") int id_user, @Param("id_noti") int id_noti);
	
}
