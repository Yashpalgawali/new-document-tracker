package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
}
