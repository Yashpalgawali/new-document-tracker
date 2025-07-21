package com.example.demo.service;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.User;

public interface UserService {

	public User saveUser(User user);
	
	public User getUserById(Long id) throws ResourceNotFoundException;
	
	public User getUserByUserName(String username);
	
	public int updateUserPassword(User user);
}
