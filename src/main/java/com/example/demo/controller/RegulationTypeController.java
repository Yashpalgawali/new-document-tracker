package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.RegulationType;
import com.example.demo.service.RegulationTypeService;

@RestController
@RequestMapping("regulationtype")
public class RegulationTypeController {

	private RegulationTypeService regulationtypeserv;
	
	public RegulationTypeController(RegulationTypeService regulationtypeserv) {
		super();
		this.regulationtypeserv = regulationtypeserv;
	}

	@PostMapping("/")
	public ResponseEntity<RegulationType> saveRegulationType(@RequestBody RegulationType rtype)
	{
		RegulationType regtype = regulationtypeserv.saveRegulationType(rtype);
		if(regtype!=null) {
			return new ResponseEntity<RegulationType>(regtype,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<RegulationType>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/csrf-token")
	public CsrfToken getCsrfToken(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf");
	}
	
	//@PreAuthorize("hasAnyRole('ROLE_VENDOR','ROLE_ADMIN')")
	@GetMapping("/")
	public ResponseEntity<List<RegulationType>> getAllRegulationTypes(HttpServletRequest request)
	{
		request.getAttribute("_csrf");
		List<RegulationType> rtypelist = regulationtypeserv.getAllRegulationTypes();
		if(rtypelist.size()>0) {
			return new ResponseEntity<List<RegulationType>>(rtypelist,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<RegulationType>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RegulationType> getRegulationTypeById(@PathVariable("id") Integer id)
	{
		RegulationType rtype = regulationtypeserv.getRegulationTypeById(id);
		if(rtype!=null) {
			return new ResponseEntity<RegulationType>(rtype,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<RegulationType>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/")
	public ResponseEntity<RegulationType> updateRegulationType(@RequestBody RegulationType rtype)
	{
		int regtype = regulationtypeserv.updateRegulationType(rtype);
		if(regtype> 0) {
			return new ResponseEntity<RegulationType>(regulationtypeserv.getRegulationTypeById(rtype.getRegulation_type_id()) ,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<RegulationType>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
