package com.example.demo.service;

import java.util.List;
import java.util.Optional;

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
		
		return notificationrepo.save(notification);
	}

	@Override
	public List<Notification> getAllNotifications() {
		return notificationrepo.findAll();
	}

	@Override
	public Notification getNotificationById(Integer nid) {
		
		Optional<Notification> notification = notificationrepo.findById(nid);
		if(!notification.isEmpty()) {
			return notification.get();
		}
		else {
			return null;
		}
		
	}

	@Override
	public int updateNotification(Notification notification) {
		return notificationrepo.updateNotification( notification.getNotification_name(), notification.getNotification_description(), notification.getNotification_start_date(), notification.getNotification_end_date(),notification.getStatus(),notification.getNotification_id());
	}

	@Override
	public List<Notification> getAllActiveNotifications(int status) {
		// TODO Auto-generated method stub
		return notificationrepo.getAllActiveNotifications(status);
	}

}
