package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.models.Regulation;
import com.example.demo.models.RegulationType;
import com.example.demo.models.Vendor;
import com.example.demo.service.RegulationService;

@RestController
@RequestMapping("regulation")
@CrossOrigin("*")
public class RegulationController {

	@Value("${spring.application.name}")
    private String contextPath;
	
	@Value("${upload.dir}")
    private String uploadPath;
	
	@Autowired
	RegulationService regulationserv;
	
	
	@PostMapping("/")
    public ResponseEntity<Regulation> handleFileUpload(
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
		
		RegulationType regtype = new RegulationType();
		regtype.setRegulation_type_id(regulation_type_id);
		regulate.setRegulationtype(regtype);
		
		
		Regulation reg = regulationserv.saveRegulation(regulate,file);
		if(reg!=null) {
			
			return new ResponseEntity<Regulation>(reg ,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Regulation>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
//	  System.err.println("inside the upload file controller \n File name is "+file.getOriginalFilename());
//        try {
//            if (!file.isEmpty()) {
//            	
//            	File vendorDir = new File(uploadPath+File.separator+"Quality"+File.separator+1);
//            	vendorDir.mkdirs();
//            	
//            	Path filePath = Paths.get(vendorDir.getAbsolutePath(), file.getOriginalFilename());
//				 try {
//					Files.copy(file.getInputStream(), filePath);
//					
//					InputStream ipstream = file.getInputStream();
//					ipstream.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//            }
//        } catch (Exception e) {
//        	System.err.println(e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
//        }
//        return ResponseEntity.ok("File uploaded successfully");
    }
	
	
	
	
//	@PostMapping(value ="/" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
//	public ResponseEntity<Regulation> saveRegulation( @RequestPart("regulation_name") String regulation_name,
//            @RequestPart("regulation_description") String regulation_description,
//            @RequestPart("regulation_frequency") String regulation_frequency,
//            @RequestPart("regulation_issued_date") String regulation_issued_date,
//            @RequestPart("regulation_type_id") Integer regulation_type_id,
//            @RequestPart(value = "file") MultipartFile file) 
//	{
//		
//		System.err.println("inside saveregulation() controller \n");
//		
//		
//		
//		Regulation regulate = new Regulation();
//		regulate.setRegulation_name(regulation_name);
//		regulate.setRegulation_description(regulation_description);
//		regulate.setRegulation_frequency(regulation_frequency);
//		regulate.setRegulation_issued_date(regulation_issued_date);
//		
//		RegulationType regtype = new RegulationType();
//		regtype.setRegulation_type_id(regulation_type_id);
//		regulate.setRegulationtype(regtype);
//		
//		
//		Regulation reg = regulationserv.saveRegulation(regulate,file);
//		if(reg!=null) {
//			
//			return new ResponseEntity<Regulation>(reg ,HttpStatus.OK);
//		}
//		else {
//			return new ResponseEntity<Regulation>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
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
	
	@PutMapping("/")
	public ResponseEntity<Regulation> updateRegulationById(@RequestBody Regulation regulation)
	{
		int result = regulationserv.updateRegulation(regulation);
		if(result>0)
			return new ResponseEntity<Regulation>(regulationserv.getRegualtionById(regulation.getRegulation_id()), HttpStatus.OK);
		else
			return new ResponseEntity<Regulation>(HttpStatus.NOT_MODIFIED);
	}
}
