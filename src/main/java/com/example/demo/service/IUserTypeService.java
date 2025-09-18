package com.example.demo.service;

import java.util.List;

import com.example.demo.models.UserType;

public interface IUserTypeService {

	public UserType saveUserType(UserType usertype);
	
	public List<UserType> getAllUserTypes();
	
	public UserType getUserTypeById(Integer usertypeid);
	
	public int updateUserType(UserType usertype);
}
