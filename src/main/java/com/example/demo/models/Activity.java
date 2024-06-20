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
@SequenceGenerator(name = "activity_seq" ,allocationSize = 1, initialValue = 1)
@Table(name="tbl_activity")
public class Activity {

	@Id
	@GeneratedValue(generator = "activity_seq" , strategy = GenerationType.AUTO)
	private Integer activity_id;
	
	private String activity;
	
	private String activity_date;
	
	private String activity_time;
	
	
}
