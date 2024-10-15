package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.models.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {

	
	private UserService userserv;

	public UserController(UserService userserv) {
		super();
		this.userserv = userserv;
	}
	

	@GetMapping("/{userid}")
	public ResponseEntity<User> findUserById(@PathVariable Integer userid)
	{
		User user = userserv.getUserById(userid);
		if(user!=null) {
		
			return  new ResponseEntity<User>(user, HttpStatus.OK);
		}
		else {
			throw new UserNotFoundException("User Is not found");
			//return  new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		
	}
}
