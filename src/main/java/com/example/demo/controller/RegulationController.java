package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Regulation;
import com.example.demo.models.Vendor;
import com.example.demo.service.RegulationService;

@RestController
@RequestMapping("regulation")
@CrossOrigin("*")
public class RegulationController {

	@Autowired
	RegulationService regulationserv;
	
	@PostMapping("/")
	public ResponseEntity<Regulation> saveRegulation(@RequestBody Regulation regulation)
	{
		System.err.println(regulation.toString()); 
		
		return new ResponseEntity<Regulation>(HttpStatus.INTERNAL_SERVER_ERROR);
//		Regulation reg = regulationserv.saveRegulation(regulation);
//		if(reg!=null) {
//			
//			return new ResponseEntity<Regulation>(reg ,HttpStatus.OK);
//		}
//		else {
//			return new ResponseEntity<Regulation>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Regulation>> getAllRegulations()
	{
		List<Regulation> reglist = regulationserv.getAllRegulations();
		if(reglist.size()>0) {
			return new ResponseEntity<List<Regulation>>(reglist , HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Regulation>>(HttpStatus.NO_CONTENT);
		}
	}
}
