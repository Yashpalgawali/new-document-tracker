package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.GlobalException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.global.GlobalVars;
import com.example.demo.models.Activity;
import com.example.demo.models.Notification;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.NotificationRepository;

@Service("notificationserv")
public class NotificationServImpl implements NotificationService {

	private NotificationRepository notificationrepo;
	
	private ActivityRepository actrepo;

	public NotificationServImpl(NotificationRepository notificationrepo, ActivityRepository actrepo) {
		super();
		this.notificationrepo = notificationrepo;
		this.actrepo = actrepo;
	}

	@Override
	public Notification saveNotification(Notification notification) {
		
		notification.setStatus(1);
		notification.setNotification_add_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()) );
		notification.setNotification_add_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()) );
		
		Notification notif = notificationrepo.save(notification);
		if(notif!=null) {
			Activity act = new Activity();
			act.setActivity("Notification "+ notification.getNotification_name()  +" is saved successfully");
			act.setActivity_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()) );
			act.setActivity_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()) );
			actrepo.save(act);
			return notif;
		}
		else {
			Activity act = new Activity();
			act.setActivity("Notification "+ notification.getNotification_name()  +" is not saved ");
			act.setActivity_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()) );
			act.setActivity_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()) );
			actrepo.save(act);
			throw new GlobalException("Notification "+notification.getNotification_name()+" is not saved");
		}
		
	}
  
	@Override
	public List<Notification> getAllNotifications() {
		
		List<Notification> notificationList = notificationrepo.findAll();
		if(notificationList.size()>0) {
			return notificationList;
		}
		throw new ResourceNotFoundException("No Notifications found");
	}

	@Override
	public Notification getNotificationById(Integer nid) {
		return notificationrepo.findById(nid).orElseThrow(()-> new ResourceNotFoundException("No notification found for given ID "+nid));
	}

	@Override
	public int updateNotification(Notification notification) {
		int result = notificationrepo.updateNotification( notification.getNotification_name(), notification.getNotification_description(), notification.getNotification_start_date(), notification.getNotification_end_date(),notification.getStatus(),notification.getNotification_id());
		if(result >0) {
			Activity act = new Activity();
			act.setActivity("Notification "+ notification.getNotification_name()  +" is updated successfully");
			act.setActivity_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()) );
			act.setActivity_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()) );
			actrepo.save(act);
		}
		else {
			Activity act = new Activity();
			act.setActivity("Notification "+ notification.getNotification_name()+" is not updated ");
			act.setActivity_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()) );
			act.setActivity_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()) );
			actrepo.save(act);
			throw new GlobalException("Notification "+notification.getNotification_name()+" is not updated");
		}
		return result;
	}

	@Override
	public List<Notification> getAllActiveNotifications() {
		 
 		List<Notification> notificationList = notificationrepo.getAllActiveNotifications();
 		if(notificationList.size() > 0) {
 			return notificationList;
 		}
 		else {
 			throw new ResourceNotFoundException("No Active Notifications found");
 		}
	}

}
