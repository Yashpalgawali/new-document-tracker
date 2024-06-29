package com.example.demo.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Activity;
import com.example.demo.models.Notification;
import com.example.demo.service.ActivityService;
import com.example.demo.service.NotificationService;

@RestController
@RequestMapping("notification")
@CrossOrigin("*")
public class NotificationController {

	@Autowired
	private NotificationService notificationserv;
	
	@Autowired
	private ActivityService actserv;
	
	@PostMapping("/")
	public ResponseEntity<Notification> saveNotification(@RequestBody Notification notification)
	{
		notification.setStatus(1);
		
		Notification notify = notificationserv.saveNotification(notification);
		if(notify!=null) {
//			activity.setActivity(notification.getNotification_name() +" is saved successfully");
//			activity.setActivity_date(""+ LocalDateTime.now());
//			activity.setActivity_time(null);
//			
//			actserv.saveActivity(activity);
			return new ResponseEntity<Notification>(notify , HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Notification>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Notification>> getAllNotifications()
	{
		
		List<Notification> allNotifications = notificationserv.getAllNotifications();
		if(allNotifications.size()>0) {
			return new ResponseEntity<List<Notification>>(allNotifications , HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Notification>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@GetMapping("/edit/{id}")
	public ResponseEntity<Notification> getNotificationById(@PathVariable("id") Integer id)
	{
		Notification nlist = notificationserv.getNotificationById(id);
		
		if(nlist!=null) {
			return new ResponseEntity<Notification>(nlist ,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Notification>(HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping("/active")
	public ResponseEntity<List<Notification>> getActiveNotifications()
	{
		List<Notification> allNotifications = notificationserv.getAllActiveNotifications(1);
		if(allNotifications.size()>0) {
			return new ResponseEntity<List<Notification>>(allNotifications , HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Notification>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PutMapping("/")
	public ResponseEntity<Notification> updateNotification(@RequestBody Notification notification)
	{
		int result = notificationserv.updateNotification(notification);
		if(result > 0) {
			return new ResponseEntity<Notification>(notificationserv.getNotificationById(notification.getNotification_id()), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Notification>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
