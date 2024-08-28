package com.example.demo.controller;

import org.springframework.http.HttpHeaders;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.models.Regulation;
import com.example.demo.models.RegulationDTO;
import com.example.demo.models.RegulationType;
import com.example.demo.models.Vendor;
import com.example.demo.service.RegulationService;
import com.example.demo.service.RegulationTypeService;
import com.example.demo.service.VendorService;

@RestController
@RequestMapping("regulation")
@CrossOrigin(origins = "http://localhost:4200")
public class RegulationController {

	@Value("${spring.application.name}")
    private String contextPath;
	
	@Value("${upload.dir}")
    private String uploadPath;
	 
	private RegulationService regulationserv;
	
	private VendorService vendserv;
	
	private RegulationTypeService regtypeserv;
	
	
	public RegulationController(RegulationService regulationserv, VendorService vendserv,
			RegulationTypeService regtypeserv) {
		super();
		this.regulationserv = regulationserv;
		this.vendserv = vendserv;
		this.regtypeserv = regtypeserv;
	}

	@PostMapping("/")
    public ResponseEntity<Regulation> saveRegulation(
            @RequestParam("regulation_name") String regulation_name,
            @RequestParam("regulation_description") String regulation_description,
            @RequestParam("regulation_frequency")  String regulation_frequency,
            @RequestParam("regulation_type_id")  Integer regulation_type_id,
            @RequestParam("regulation_issued_date")  String regulation_issued_date,
            
            @RequestParam("file") MultipartFile file) {
        // Handle the uploaded file and other data here
        // For example, save the file to a local directory

		Regulation regulate = new Regulation();
		regulate.setRegulation_name(regulation_name);
		regulate.setRegulation_description(regulation_description);
		regulate.setRegulation_frequency(regulation_frequency);
		regulate.setRegulation_issued_date(regulation_issued_date);
		
		RegulationType regtype = regtypeserv.getRegulationTypeById(regulation_type_id);
		
		regulate.setRegulationtype(regtype);
		
		Vendor vend = vendserv.getVendorById(2);
		regulate.setVendor(vend);
		
		
		Regulation reg = regulationserv.saveRegulation(regulate,file);
		if(reg!=null) {
			
			return new ResponseEntity<Regulation>(reg ,HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<Regulation>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

    }
	
	// Using the DTO class
	@GetMapping(value= "/")
	public ResponseEntity<List<RegulationDTO>> getAllRegulations()
	{
		List<Regulation> reglist = regulationserv.getAllRegulations();
		
		List<RegulationDTO> regulationDTOs = new ArrayList<>();
		
		for(Regulation regulation : reglist)
		{
			RegulationDTO rdto = new RegulationDTO();
			rdto.setRegulation_id(regulation.getRegulation_id());
			rdto.setRegulation_name(regulation.getRegulation_name());
			rdto.setRegulation_description(regulation.getRegulation_description());
			rdto.setRegulation_frequency(regulation.getRegulation_frequency());
			rdto.setRegulation_issued_date(regulation.getRegulation_issued_date());
			rdto.setFile_name(regulation.getFile_name());
			rdto.setFile_path(regulation.getFile_path());
			rdto.setVendor(regulation.getVendor());
			rdto.setRegulation_type(regulation.getRegulationtype());
			
			regulationDTOs.add(rdto);
			
		}
		if(reglist.size()>0) {
			return new ResponseEntity<List<RegulationDTO>>(regulationDTOs , HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<RegulationDTO>>(HttpStatus.NO_CONTENT);
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
	
	@PutMapping("/")
	public ResponseEntity<Regulation> updateRegulationById( @RequestParam("regulation_name") String regulation_name,
            @RequestParam("regulation_description") String regulation_description,
            @RequestParam("regulation_frequency")  String regulation_frequency,
            @RequestParam("regulation_type_id")  Integer regulation_type_id,
            @RequestParam("regulation_issued_date")  String regulation_issued_date,
            @RequestParam("regulation_id") Integer regulation_id,
            @RequestParam("file") MultipartFile file)
	{

		Regulation regulate = new Regulation();
		regulate.setRegulation_id(regulation_id);
		regulate.setRegulation_name(regulation_name);
		regulate.setRegulation_description(regulation_description);
		regulate.setRegulation_frequency(regulation_frequency);
		regulate.setRegulation_issued_date(regulation_issued_date);
		
		RegulationType regtype = regtypeserv.getRegulationTypeById(regulation_type_id);
		
		regulate.setRegulationtype(regtype);
		
		Vendor vend = vendserv.getVendorById(2);
		regulate.setVendor(vend);
		
		int res = regulationserv.updateRegulation(regulate, file);
		if(res>0) {
			return new ResponseEntity<Regulation>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Regulation>(HttpStatus.NOT_MODIFIED);
		}	
	}
 	
	
	 @GetMapping("/pdf/id/{id}")
	    public ResponseEntity<Resource> getPdf(@PathVariable("id")Integer id) {
	        try {
	        	Regulation reg = regulationserv.getRegualtionById(id); 
	        	if(reg!=null)
	        	{ 
		        	// Construct the absolute path to the file
		            String filePath = reg.getFile_path() + "/" + reg.getFile_name();
		            Resource pdfFile = new FileSystemResource(filePath);
		            
		            if (!pdfFile.exists()) {
		                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		            }
		        	
		            HttpHeaders headers = new HttpHeaders();
		           // String encodedFilename = URLEncoder.encode(reg.getFile_name(), "UTF-8");
		            
		            String encodedFilename = reg.getFile_name() ;  
		            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + encodedFilename + "\"");
		            headers.setContentType(MediaType.APPLICATION_PDF); // Set MIME type to PDF

		            System.out.println("Content-Disposition: " + headers.getFirst(HttpHeaders.CONTENT_DISPOSITION));

		            return new ResponseEntity<Resource>(pdfFile, headers, HttpStatus.OK);
	        	}
	        	else {
	        		 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        	}
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

}
