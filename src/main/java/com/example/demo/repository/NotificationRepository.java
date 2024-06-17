package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Notification;

@Repository("notificationrepo")
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

	
	@Query("SELECT n FROM Notification n WHERE n.status=:status")
	public List<Notification> getAllActiveNotifications(int status);
	
	@Query("")
	public int updateNotification(Integer nid,String name,String description,String start_date,String end_date);
}
