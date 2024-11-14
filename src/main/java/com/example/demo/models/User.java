package com.example.demo.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@SequenceGenerator(name = "user_seq" ,allocationSize = 1, initialValue = 1)
@Table(name="tbl_user")
public class User {

	@Id
	@GeneratedValue(generator = "user_seq" , strategy = GenerationType.AUTO)
	private Integer userid;
	
	private String username;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	@Transient
	private String cnf_password;
	
	private Integer enabled;
	
	private String role;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_type_id")
	private UserType usertype;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCnf_password() {
		return cnf_password;
	}

	public void setCnf_password(String cnf_password) {
		this.cnf_password = cnf_password;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public UserType getUsertype() {
		return usertype;
	}

	public void setUsertype(UserType usertype) {
		this.usertype = usertype;
	}

	public User(Integer userid, String username, String email, String password, String cnf_password, Integer enabled,
			String role, UserType usertype) {
		super();
		this.userid = userid;
		this.username = username;
		this.email = email;
		this.password = password;
		this.cnf_password = cnf_password;
		this.enabled = enabled;
		this.role = role;
		this.usertype = usertype;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

}
