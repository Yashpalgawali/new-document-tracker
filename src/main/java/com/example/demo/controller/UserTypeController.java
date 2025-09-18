package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDto;
import com.example.demo.models.UserType;
import com.example.demo.service.IUserTypeService;

@RestController
@RequestMapping("usertype")
public class UserTypeController {

	private final IUserTypeService usertypeserv;

	public UserTypeController(IUserTypeService usertypeserv) {
		super();
		this.usertypeserv = usertypeserv;
	}

	@PostMapping("/")
	public ResponseEntity<ResponseDto> saveUserType(@RequestBody UserType usertype) {

		UserType userType = usertypeserv.saveUserType(usertype);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(HttpStatus.CREATED.toString(),
				"User Type " + userType.getUser_type() + " is saved successfully"));

	}

	@GetMapping("/")
	public ResponseEntity<List<UserType>> getAllUserTypes() {
		List<UserType> usertypelist = usertypeserv.getAllUserTypes();
		return ResponseEntity.status(HttpStatus.OK).body(usertypelist);
	}

	@PutMapping("/")
	public ResponseEntity<ResponseDto> updateUserType(@RequestBody UserType usertype) {
		usertypeserv.updateUserType(usertype);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.toString(),
				"User Type " + usertype.getUser_type() + " is saved successfully"));

	}

	@GetMapping("/{id}")
	public ResponseEntity<UserType> getUserTypeById(@PathVariable Integer id) {
		UserType usertype = usertypeserv.getUserTypeById(id);
		return ResponseEntity.status(HttpStatus.OK).body(usertype);
	}
}
