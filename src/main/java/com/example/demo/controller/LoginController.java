package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.User;
import com.example.demo.models.Vendor;
import com.example.demo.service.UserService;
import com.example.demo.service.VendorService;

@RestController
//@SessionAttributes("session_id") // Indicate which model attributes should be stored in the session
public class LoginController {


	private final UserService userservice;
	private final VendorService vendorserv;

	public LoginController(UserService userservice, VendorService vendorserv) {
		super();
		this.userservice = userservice;
		this.vendorserv = vendorserv;
	}

	@Autowired
	HttpSession session;

	//@GetMapping("basicauth")
//	public User homePage(HttpServletRequest request) {
	@PostMapping("basicauth")
	public User homePage(Authentication auth,HttpServletRequest request) throws ResourceNotFoundException {
		auth = SecurityContextHolder.getContext().getAuthentication();
		System.err.println("Logged in user is = "+auth.toString()+"\n session in request obj "+request.getSession().getId());
		session = request.getSession();
		session.setAttribute("user_name", auth.getName());
		System.err.println("\n session in session obj "+request.getSession().getId());
		
		User user = userservice.getUserByUserName(auth.getName());
		
		Vendor vendor = vendorserv.getVendorByUserId(user.getUserid());
		session.setAttribute("vendor_id", vendor.getVendor_id());
		return user;
	}
	
	
	//@GetMapping("basicauth")
	//public User authenticateBean(Authentication auth,HttpServletRequest request, Model model)
	public User authenticateBean(HttpServletRequest request) throws ResourceNotFoundException
	{
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        session = request.getSession();
        String sessionId = session.getId();
       
        session.setAttribute("session_id", sessionId); // Store session ID in HttpSession

        System.err.println("Inside Authenticate() Session ID set: " + sessionId); // Debugging output
 
		
		session = request.getSession();
        //String sessionId = sess.getId();
      
        session.setAttribute("session_id", session.getId()); // Store session ID in HttpSession
		
		User user = userservice.getUserByUserName(authentication.getName());
		System.err.println("Inside basic auth USER= "+user.toString());
		
		//Vendor vendor = vendorserv.getVendorByUserId(user.getUserid());
		Vendor vendor = vendorserv.getVendorByUserId(2L);
		System.err.println("Inside basic auth VENDOR= "+vendor.toString());
		
		session.setAttribute("vendor_id", vendor.getVendor_id());
		System.err.println("Inside Basic auth \n Session ID is "+ request.getRequestedSessionId()+" \n vendor Id in session is "+session.getAttribute("vendor_id"));
		
		return user;
	}
}