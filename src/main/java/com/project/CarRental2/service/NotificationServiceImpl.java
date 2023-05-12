package com.project.CarRental2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.CarRental2.model.Notification;
import com.project.CarRental2.repository.NotificationRepository;

@Service
public class NotificationServiceImpl  implements NotificationService{

	@Autowired
	private NotificationRepository repo;
	@Override
	public List<Notification> getAllNotification() {
		
		return repo.findAll();
	}

	@Override
	public void saveNotification(Notification noti) {
		repo.save(noti);
	}

	@Override
	public void deleteNotification(int idNotifi) {
		repo.deleteById(idNotifi);
		
	}

	@Override
	public Notification getANotification(int idNoti) {
		
		return repo.findById(idNoti).get();
	}

	@Override
	public Notification getNotificationLaster() {
		
		return repo.getNotificationLaster();
	}

	@Override
	public List<Notification> getNotificationByIdUser(int idUser) {
		return repo.getNotificationByIdUser(idUser);
	}
}
