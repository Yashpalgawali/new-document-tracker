package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Notification;
import com.example.demo.service.NotificationService;

@RestController
@RequestMapping("notification")
public class NotificationController {

	@Autowired
	private NotificationService notificationserv;
	
	@PostMapping("/")
	public ResponseEntity<Notification> saveNotification(@RequestBody Notification notification)
	{
		Notification savedNotification = notificationserv.saveNotification(notification);
		if(savedNotification!=null) {
		
			return new ResponseEntity<Notification>(savedNotification , HttpStatus.OK);
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
	

	@GetMapping("/{id}")
	public ResponseEntity<List<Notification>> getActiveNotifications(@PathVariable("id") int status)
	{
		List<Notification> allNotifications = notificationserv.getAllActiveNotifications(status);
		if(allNotifications.size()>0) {
		
			return new ResponseEntity<List<Notification>>(allNotifications , HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Notification>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/")
	public ResponseEntity<Notification> updateNotification(@RequestBody Notification notification)
	{
		int result = notificationserv.updateNotification(notification);
		if(result > 0) {
		
			return new ResponseEntity<Notification>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Notification>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
