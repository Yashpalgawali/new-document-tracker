package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDto;
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
	public ResponseEntity<ResponseDto> saveRegulationType(@RequestBody RegulationType rtype)
	{
		regulationtypeserv.saveRegulationType(rtype);		
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(HttpStatus.CREATED.toString(),"Regulation Type "+rtype.getRegulation_type()+" is saved successfully"));		
	}
	
	@GetMapping("/csrf-token")
	public CsrfToken getCsrfToken(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf");
	}
	
	@GetMapping("/")
	public ResponseEntity<List<RegulationType>> getAllRegulationTypes(HttpServletRequest request)
	{
		List<RegulationType> rtypelist = regulationtypeserv.getAllRegulationTypes();
		return new ResponseEntity<List<RegulationType>>(rtypelist,HttpStatus.OK);		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RegulationType> getRegulationTypeById(@PathVariable Integer id)
	{
		RegulationType rtype = regulationtypeserv.getRegulationTypeById(id);		 
		return ResponseEntity.status(HttpStatus.OK).body(rtype);		 
	}
	
	@PutMapping("/")
	public ResponseEntity<ResponseDto> updateRegulationType(@RequestBody RegulationType rtype)
	{
		regulationtypeserv.updateRegulationType(rtype);
		return  ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK.toString(),"Regulation Type "+rtype.getRegulation_type()+" is updated successfully"));		
	}
}
