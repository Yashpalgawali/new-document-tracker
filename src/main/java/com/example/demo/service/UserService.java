package com.example.demo.service;

import com.example.demo.models.User;

public interface UserService {

	public User saveUser(User user);
	
	public User getUserById(Long id);
	
	public User getUserByUserName(String username);
	
	public User getUserByEmail(String email);
	
	public int updateUserPassword(User user);
}
