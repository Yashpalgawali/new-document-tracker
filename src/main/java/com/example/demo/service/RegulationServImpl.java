package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.models.Regulation;
import com.example.demo.repository.RegulationRepository;

@Service("regulationserv")
public class RegulationServImpl implements RegulationService {
	
	@Value("${spring.application.name}")
    private String contextPath;
	
	@Value("${upload.dir}")
    private String uploadPath;
	
	@Autowired
	RegulationRepository regulationrepo;
	
	@Override
	public Regulation saveRegulation(Regulation regulation,MultipartFile file) {
		
		System.err.println("Inside saveregualtion() \n "+regulation.toString()+"\n");
		 String filename = file.getOriginalFilename();
	     String filepath = "";  
	     
	     if (file != null) {
	    	 File uploadDirectory = new File(uploadPath);
	    	 if(!uploadDirectory.exists())
	    	 {
	    		 boolean created = uploadDirectory.mkdirs();
	    		 if(created)
	    		 {
	    			 File vendorDir = new File(uploadPath+File.separator+"Quality"+File.separator+1);
	    			 filepath =  vendorDir.getAbsolutePath();
	    			 if(!vendorDir.exists()) 
	    			 {
	    				 boolean create = vendorDir.mkdirs();
	    				 if(create) {
	    					 
	    					 Path filePath = Paths.get(vendorDir.getAbsolutePath(), filename);
	    					 try {
								Files.copy(file.getInputStream(), filePath);
								
								InputStream ipstream = file.getInputStream();
								ipstream.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	    				 }
	    			 }
	    			 else {
	    				 Path filePath = Paths.get(vendorDir.getAbsolutePath(), filename);
    					 try {
							Files.copy(file.getInputStream(), filePath);
							
							InputStream ipstream = file.getInputStream();
							ipstream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    			 }
	    		 }
	    	 }
	    	 else {
	    		 File vendorDir = new File(uploadPath+File.separator+"Quality"+File.separator+1);
	    		 filepath =  vendorDir.getAbsolutePath();
    			 if(!vendorDir.exists()) {
    				 boolean create = vendorDir.mkdirs();
    				 if(create) {
    					 
    					 Path filePath = Paths.get(vendorDir.getAbsolutePath(), filename);
    					 try {
							Files.copy(file.getInputStream(), filePath);
							
							InputStream ipstream = file.getInputStream();
							ipstream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    				 }
    			 }
    			 else {
    				 Path filePath = Paths.get(vendorDir.getAbsolutePath(), filename);
					 try {
						Files.copy(file.getInputStream(), filePath);
						
						InputStream ipstream = file.getInputStream();
						ipstream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			 }
	     }

	} 
	      regulation.setFile_name(filename);
	      regulation.setFile_path(filepath);
		System.err.println("\n After uploading file to server data is \n "+regulation.toString()+"\n");
		return regulationrepo.save(regulation);
	}

	@Override
	public List<Regulation> getAllRegulations() {
		return regulationrepo.findAll();
	}

	@Override
	public Regulation getRegualtionById(Integer id) {
		Optional<Regulation> optional = regulationrepo.findById(id);
		if(optional.isPresent())
			return optional.get();
		else
			return null;
	}

	@Override
	public int updateRegulation(Regulation regulation) {
		// TODO Auto-generated method stub
		return regulationrepo.updateRegulationById(regulation.getRegulation_name(), regulation.getRegulation_description(), regulation.getRegulation_frequency(), regulation.getRegulation_issued_date(), regulation.getFile_name(), regulation.getFile_path(), regulation.getRegulationtype().getRegulation_type_id(), regulation.getVendor().getVendor_id(),regulation.getRegulation_id());
	}

	@Override
	public List<Regulation> getAllRegulationsByVendorId(Integer id) {
		
		return regulationrepo.getRegulationByVendorId(id);
	}

	@Override
	public List<Regulation> getRegulationsByVendorIdAndRegulationId(Integer vid, Integer rid) {
		
		return regulationrepo.getRegulationByVendorIdAndRegulationId(vid, rid);
	}

}
