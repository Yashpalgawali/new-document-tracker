package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Notification;
import com.example.demo.repository.NotificationRepository;

@Service("notificationserv")
public class NotificationServImpl implements NotificationService {

	@Autowired
	NotificationRepository notificationrepo;
	
	@Override
	public Notification saveNotification(Notification notification) {
		// TODO Auto-generated method stub
		return notificationrepo.save(notification);
	}

	@Override
	public List<Notification> getAllNotifications() {
		// TODO Auto-generated method stub
		return notificationrepo.findAll();
	}

	@Override
	public Notification getNotificationById(Integer nid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateNotification(Notification notification) {
		// TODO Auto-generated method stub
		return notificationrepo.updateNotification(notification.getNotification_id(), notification.getNotification_name(), notification.getNotification_description(), notification.getNotification_start_date(), notification.getNotification_end_date());
	}

	@Override
	public List<Notification> getAllActiveNotifications(int status) {
		// TODO Auto-generated method stub
		return notificationrepo.getAllActiveNotifications(status);
	}

}
