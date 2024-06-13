package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		
		Regulation reg = regulationserv.saveRegulation(regulation);
		if(reg!=null) {
			
			return new ResponseEntity<Regulation>(reg ,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Regulation>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
	
	@GetMapping("/{id}")
	public ResponseEntity<Regulation> getRegulationById(@PathVariable("id") Integer id)
	{
		Regulation regulation = regulationserv.getRegualtionById(id);
		if(regulation!=null) {
			return new ResponseEntity<Regulation>(regulation,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Regulation>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/vendor/{id}")
	public ResponseEntity<List<Regulation>> getAllRegulationsByVendorId(@PathVariable("id")Integer id)
	{
		List<Regulation> reglist = regulationserv.getAllRegulationsByVendorId(id);
		if(reglist.size()>0)
			return new ResponseEntity<List<Regulation>>(reglist, HttpStatus.OK);
		else
			return new ResponseEntity<List<Regulation>>(HttpStatus.NO_CONTENT); 
	}
	
	
	@GetMapping("/vendor/{id}/regulation/{rid}")
	// This method will return the regulation of a particular vendor by vendor ID and regulation ID 
	public ResponseEntity<List<Regulation>> getRegulationsByVendorIdAndRegulationId(@PathVariable("id")Integer id,@PathVariable("rid")Integer rid)
	{
		List<Regulation> reglist = regulationserv.getRegulationsByVendorIdAndRegulationId(id, rid);
		if(reglist.size()>0)
			return new ResponseEntity<List<Regulation>>(reglist, HttpStatus.OK);
		else
			return new ResponseEntity<List<Regulation>>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("")
	public ResponseEntity<Regulation> updateRegulationById(@RequestBody Regulation regulation)
	{
		int result = regulationserv.updateRegulation(regulation);
		if(result>0)
			return new ResponseEntity<Regulation>(regulationserv.getRegualtionById(regulation.getRegulation_id()), HttpStatus.OK);
		else
			return new ResponseEntity<Regulation>(HttpStatus.NOT_MODIFIED);
	}
}
