package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.RegulationHistory;
import com.example.demo.service.RegulationHistoryService;

@RestController
@RequestMapping("regulationhist")
@CrossOrigin("*")
public class RegulationHistoryController {

	private RegulationHistoryService reghistserv;
	
	public RegulationHistoryController(RegulationHistoryService reghistserv) {
		super();
		this.reghistserv = reghistserv;
	}


	@GetMapping("/history/{id}")
	 public ResponseEntity<List<RegulationHistory>> getRegulationHistoryByRegulationId(@PathVariable("id") Integer id) {
		 List<RegulationHistory> reglist = reghistserv.getRegulationHistoryByRegulationId(id);
		 return new ResponseEntity<List<RegulationHistory>>(reglist,HttpStatus.OK);
	 }
}
