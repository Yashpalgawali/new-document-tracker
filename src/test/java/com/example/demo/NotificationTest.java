package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.example.demo.models.Notification;
import com.example.demo.service.NotificationService;

@SpringBootTest(classes = NewDocumentTrackerApplication.class)
class NotificationTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private NotificationService notificationserv;
	
	@Autowired
	public NotificationTest(NotificationService notificationserv) {
		this.notificationserv = notificationserv;
	}
	
	@Autowired
	EntityManager em;
	
	@Test @DirtiesContext
	void saveNotification() {
//		Notification notif = new Notification("ISO","Update ISO Certificates","13-07-2024","12:12:47","13-07-2024","01-08-2024",1);
//	
//		 em.persist(notif);
//		 em.flush();
//		assertEquals("ISO", notif.getNotification_name());
//		logger.info("Notification is = {} "+em.find(Notification.class, notif.getNotification_id()));
	}
	
	@Test
	void getAllNotifications()
	{
		TypedQuery<Notification> result = em.createNamedQuery("query_get_all_notifications",Notification.class );
		
		List<Notification> resultList = result.getResultList();
		logger.info("All Notifications are {} "+resultList);
	}
	
	@Test
	void getNotificationById()
	{
		 
		Notification obj =  notificationserv.getNotificationById(3);
		logger.info("All Notifications are {} "+obj.toString());
	}
	


}
