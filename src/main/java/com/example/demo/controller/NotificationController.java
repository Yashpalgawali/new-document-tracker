package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDto;
import com.example.demo.models.Notification;
import com.example.demo.service.NotificationService;

@RestController
@RequestMapping("notification")
public class NotificationController { 

	private final NotificationService notificationserv; 

	public NotificationController(NotificationService notificationserv ) {
		super();
		this.notificationserv = notificationserv; 
	}

	@PostMapping("/")
	public ResponseEntity<ResponseDto> saveNotification(@RequestBody Notification notification) {
		Notification notify = notificationserv.saveNotification(notification);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(HttpStatus.CREATED.toString(),"Notification "+notify.getNotification_name()+" is saved successfully"));
	}

	@GetMapping("/")
	public ResponseEntity<List<Notification>> getAllNotifications()  { 
		List<Notification> allNotifications = notificationserv.getAllNotifications();
		return ResponseEntity.status(HttpStatus.OK).body(allNotifications);

	}

	@GetMapping("/edit/{id}")
	public ResponseEntity<Notification> getNotificationById(@PathVariable Integer id) {
		Notification nlist = notificationserv.getNotificationById(id);		 
		return ResponseEntity.status(HttpStatus.OK).body(nlist);
		
	}

	@GetMapping("/active")
	public ResponseEntity<List<Notification>> getActiveNotifications()
	{
		List<Notification> allNotifications = notificationserv.getAllActiveNotifications();
		return ResponseEntity.status(HttpStatus.OK).body(allNotifications);		
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