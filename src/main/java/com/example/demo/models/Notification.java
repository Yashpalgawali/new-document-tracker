package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

	public Notification(String notification_name, String notification_description, String notification_add_date,
			String notification_add_time, String notification_start_date, String notification_end_date, int status) {
		super();
		this.notification_name = notification_name;
		this.notification_description = notification_description;
		this.notification_add_date = notification_add_date;
		this.notification_add_time = notification_add_time;
		this.notification_start_date = notification_start_date;
		this.notification_end_date = notification_end_date;
		this.status = status;
	}
	
	
}
