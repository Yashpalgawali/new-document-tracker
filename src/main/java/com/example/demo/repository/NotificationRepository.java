package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Notification;

@Repository("notificationrepo")
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

	
	@Query("SELECT n FROM Notification n WHERE n.status=:status")
	public List<Notification> getAllActiveNotifications(int status);
	
	@Modifying
	@Transactional
	@Query("UPDATE Notification n set n.notification_name=:nname,n.notification_description=:ndesc,n.notification_start_date=:startdate,n.notification_end_date=:enddate,n.status=:status WHERE n.notification_id=:nid")
	public int updateNotification(String nname,String ndesc,String startdate,String enddate,int status,Integer nid);
}
