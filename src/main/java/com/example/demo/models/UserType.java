package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tbl_user_type")
@SequenceGenerator(name = "user_type_seq" , allocationSize = 1, initialValue = 1)
public class UserType {

	@Id
	@GeneratedValue(generator = "user_type_seq" , strategy = GenerationType.AUTO)
	private Integer user_type_id;
	
	private String user_type;

	public Integer getUser_type_id() {
		return user_type_id;
	}

	public void setUser_type_id(Integer user_type_id) {
		this.user_type_id = user_type_id;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public UserType(Integer user_type_id, String user_type) {
		super();
		this.user_type_id = user_type_id;
		this.user_type = user_type;
	}

	public UserType() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
