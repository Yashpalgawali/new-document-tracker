package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {

	private final UserService userserv;

	public UserController(UserService userserv) {
		super();
		this.userserv = userserv;
	}

	@GetMapping("/{userid}")
	public ResponseEntity<User> findUserById(@PathVariable Long userid) throws ResourceNotFoundException {
		User user = userserv.getUserById(userid);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PutMapping("/")
	public ResponseEntity<String> changePassword(@RequestBody User user) {

		int res = userserv.updateUserPassword(user);
		if (res > 0) {
			return new ResponseEntity<String>("Password is updated successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Password is not updated", HttpStatus.NOT_MODIFIED);
		}
	}
}
