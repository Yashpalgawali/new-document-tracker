package com.example.demo.service;

import java.util.List;

import com.example.demo.models.Notification;

public interface NotificationService {

	
	public Notification saveNotification(Notification notification);
	
	public List<Notification> getAllNotifications();
	
	public Notification getNotificationById(Integer nid);
	
	public int updateNotification(Notification notification);
	
	public List<Notification> getAllActiveNotifications();
}
