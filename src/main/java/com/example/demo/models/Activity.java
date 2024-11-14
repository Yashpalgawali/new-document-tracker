package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name = "activity_seq" ,allocationSize = 1, initialValue = 1)
@Table(name="tbl_activity")
public class Activity {

	@Id
	@GeneratedValue(generator = "activity_seq" , strategy = GenerationType.AUTO)
	private Integer activity_id;
	
	private String activity;
	
	private String activity_date;
	
	private String activity_time;

	/**
	 * @param activity_id
	 * @param activity
	 * @param activity_date
	 * @param activity_time
	 */
	public Activity(Integer activity_id, String activity, String activity_date, String activity_time) {
		super();
		this.activity_id = activity_id;
		this.activity = activity;
		this.activity_date = activity_date;
		this.activity_time = activity_time;
	}

	public Integer getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(Integer activity_id) {
		this.activity_id = activity_id;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getActivity_date() {
		return activity_date;
	}

	public void setActivity_date(String activity_date) {
		this.activity_date = activity_date;
	}

	public String getActivity_time() {
		return activity_time;
	}

	public void setActivity_time(String activity_time) {
		this.activity_time = activity_time;
	}

	/**
	 * 
	 */
	public Activity() {
		super();
	
	}
}
