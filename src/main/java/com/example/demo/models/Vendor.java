package com.example.demo.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@SequenceGenerator(name = "vendor_seq" ,allocationSize = 1, initialValue = 1)
@Table(name="tbl_vendor")
public class Vendor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator = "vendor_seq" )
	private Integer vendor_id;
	
	private String vendor_name;
	
	private String vendor_email;
	 
	@Transient
	private String username;
	
	@Transient
	private String password;
	  
	@JsonIgnore
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id")
	private User user; 
	
	private Integer enabled;

	public Integer getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(Integer vendor_id) {
		this.vendor_id = vendor_id;
	}

	public String getVendor_name() {
		return vendor_name;
	}

	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}

	public String getVendor_email() {
		return vendor_email;
	}

	public void setVendor_email(String vendor_email) {
		this.vendor_email = vendor_email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Vendor(Integer vendor_id, String vendor_name, String vendor_email, String username, String password,
			User user, Integer enabled) {
		super();
		this.vendor_id = vendor_id;
		this.vendor_name = vendor_name;
		this.vendor_email = vendor_email;
		this.username = username;
		this.password = password;
		this.user = user;
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "Vendor [vendor_id=" + vendor_id + ", vendor_name=" + vendor_name + ", vendor_email=" + vendor_email
				+ ", username=" + username + ", password=" + password + ", user=" + user + ", enabled=" + enabled + "]";
	}

	public Vendor() {
		super();
	}
	
}
