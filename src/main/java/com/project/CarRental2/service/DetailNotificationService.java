package com.project.CarRental2.service;

import java.util.List;

import com.project.CarRental2.model.DetailNotification;

public interface DetailNotificationService {
	void saveDetail(DetailNotification detailNoti);
	
	List<DetailNotification> getListDetailNotification(int idUser);
	void updateStatusAllByIDUserNotification(int status, int id_user);
	void updateStatusNotificationByIDNotiAndIDUser(int status, int id_user, int id_notifiation);
}
