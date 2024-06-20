package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.models.Activity;
import com.example.demo.repository.ActivityRepository;

@Service
public class ActivityServImpl implements ActivityService {

	@Autowired
	ActivityRepository actrepo;
	
	@Override
	public Activity saveActivity(Activity activity) {
		return actrepo.save(activity);
	}

	@Override
	public List<Activity> getAllActivities() {

		return actrepo.findAll();
	}

}
