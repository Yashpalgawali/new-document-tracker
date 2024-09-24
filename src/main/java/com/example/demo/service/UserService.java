package com.example.demo.service;

import com.example.demo.models.User;

public interface UserService {

	public User saveUser(User user);
	
	public User getUserById(Integer id);
	
	public User getUserByUserName(String username);
}
