package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@SequenceGenerator(name="notification_seq", allocationSize = 1 , initialValue = 1)
@Table(name="tbl_notification")
//@NamedQuery(name="query_get_all_notifications",query = "SELECT n FROM Notification n")
public class Notification {

	@Id
	@GeneratedValue(generator = "notification_seq",strategy = GenerationType.AUTO)
	private Integer notification_id;
	
	private String notification_name;
	
	private String notification_description;
	
	private String notification_add_date;
	
	private String notification_add_time;
	
	private String notification_start_date;
	
	private String notification_end_date;
	
	private int status;

 	public Integer getNotification_id() {
		return notification_id;
	}

	public void setNotification_id(Integer notification_id) {
		this.notification_id = notification_id;
	}

	public String getNotification_name() {
		return notification_name;
	}

	public void setNotification_name(String notification_name) {
		this.notification_name = notification_name;
	}

	public String getNotification_description() {
		return notification_description;
	}

	public void setNotification_description(String notification_description) {
		this.notification_description = notification_description;
	}

	public String getNotification_add_date() {
		return notification_add_date;
	}

	public void setNotification_add_date(String notification_add_date) {
		this.notification_add_date = notification_add_date;
	}

	public String getNotification_add_time() {
		return notification_add_time;
	}

	public void setNotification_add_time(String notification_add_time) {
		this.notification_add_time = notification_add_time;
	}

	public String getNotification_start_date() {
		return notification_start_date;
	}

	public void setNotification_start_date(String notification_start_date) {
		this.notification_start_date = notification_start_date;
	}

	public String getNotification_end_date() {
		return notification_end_date;
	}

	public void setNotification_end_date(String notification_end_date) {
		this.notification_end_date = notification_end_date;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Notification(Integer notification_id, String notification_name, String notification_description,
			String notification_add_date, String notification_add_time, String notification_start_date,
			String notification_end_date, int status) {
		super();
		this.notification_id = notification_id;
		this.notification_name = notification_name;
		this.notification_description = notification_description;
		this.notification_add_date = notification_add_date;
		this.notification_add_time = notification_add_time;
		this.notification_start_date = notification_start_date;
		this.notification_end_date = notification_end_date;
		this.status = status;
	}

	public Notification() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
