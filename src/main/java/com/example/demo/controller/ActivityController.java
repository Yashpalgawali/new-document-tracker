package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Activity;
import com.example.demo.service.ActivityService;

@RestController
@RequestMapping("activity")
@CrossOrigin("*")
public class ActivityController {

	private ActivityService actserv;
	 
	public ActivityController(ActivityService actserv) {
		super();
		this.actserv = actserv;
	}

	@GetMapping("/")
	public ResponseEntity<List<Activity>> getAllActivities()
	{
		List<Activity> actlist = actserv.getAllActivities(); 
		
		if(actlist.size()>0) {
			return new ResponseEntity<List<Activity>>(actlist, HttpStatus.OK); 
		}
		else {
			return new ResponseEntity<List<Activity>>( HttpStatus.NO_CONTENT);
		}
	}
}
