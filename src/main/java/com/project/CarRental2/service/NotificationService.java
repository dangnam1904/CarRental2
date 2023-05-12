package com.project.CarRental2.service;

import java.util.List;

import com.project.CarRental2.model.Notification;

public interface NotificationService {
	List<Notification> getAllNotification();
	void saveNotification(Notification noti);
	void deleteNotification( int idNotifi);
	Notification getANotification ( int idNoti);
	Notification getNotificationLaster();
	List<Notification> getNotificationByIdUser(int idUser);

}
