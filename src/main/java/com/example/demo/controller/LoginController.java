package com.example.demo.controller;

import org.springframework.security.core.Authentication;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("login")
@CrossOrigin(origins = "http://localhost:4200") // Allow specific origin

public class LoginController {

	@PostMapping("basicauth")
	public String authenticateBean(Authentication auth)
	{
		return "Successful";
	}
}
