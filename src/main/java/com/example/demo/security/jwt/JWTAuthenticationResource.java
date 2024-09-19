package com.example.demo.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JWTAuthenticationResource {

	@PostMapping("/authenticate")
	public 	Authentication authenticate(Authentication auth) {
		return auth;
	}
}
