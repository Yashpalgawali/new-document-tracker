package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.User;
import com.example.demo.models.Vendor;
import com.example.demo.service.UserService;
import com.example.demo.service.VendorService;

@RestController
public class LoginController {

//	@GetMapping("basicauth")
//	public String authenticateBean(Authentication auth)
//	{System.err.println("successful");
//		return "Successful";
//	}
 
//	@GetMapping("basicauth")
//	public AuthenticationBean authenticateBean(Authentication auth)
//	{ 
//		return new AuthenticationBean("Authticated successfully");
//	}
 
	private UserService userservice;
	
	private VendorService vendorserv;
	
	public LoginController(UserService userservice, VendorService vendorserv) {
		super();
		this.userservice = userservice;
		this.vendorserv = vendorserv;
	}
	
	@GetMapping("basicauth")
	public User authenticateBean(Authentication auth,HttpServletRequest request)
	{ 	
		HttpSession sess = request.getSession();
		User user = userservice.getUserByUserName(auth.getName());
		System.err.println("Inside basic auth USER= "+user.toString());
		
		Vendor vendor = vendorserv.getVendorByUserId(user.getUserid());
		System.err.println("Inside basic auth VENDOR= "+vendor.toString());
		
		sess.setAttribute("vendor_id", vendor.getVendor_id());
		System.err.println("Session ID is "+ request.getRequestedSessionId()+" \n vendor Id in session is "+sess.getAttribute("vendor_id"));
		
		return user;
	}
}