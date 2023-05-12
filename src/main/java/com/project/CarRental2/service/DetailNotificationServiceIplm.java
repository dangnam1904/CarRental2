package com.project.CarRental2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.CarRental2.model.DetailNotification;
import com.project.CarRental2.repository.DetailNotificationRepository;

@Service
public class DetailNotificationServiceIplm implements DetailNotificationService {
	@Autowired
	private DetailNotificationRepository repo;

	@Override
	public void saveDetail(DetailNotification detailNoti) {
		repo.save(detailNoti);
	}

	@Override
	public List<DetailNotification> getListDetailNotification(int idUser) {
		
		return repo.getDetailNotification(idUser);
	}
	@Override
	public void updateStatusAllByIDUserNotification(int status, int id_user) {
		repo.updateStatusAllByIDUserNotification(status, id_user);
		
	}
	@Override
	public void updateStatusNotificationByIDNotiAndIDUser(int status, int id_user, int id_notifiation) {
		repo.updateStatusNotificationByIDNotiAndIDUser(status, id_user, id_notifiation);
	}
}
