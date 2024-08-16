package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.models.Activity;
import com.example.demo.models.Regulation;
import com.example.demo.models.RegulationHistory;
import com.example.demo.repository.RegulationHistoryRepo;
import com.example.demo.repository.RegulationRepository;

@Service("regulationserv")
public class RegulationServImpl implements RegulationService {
	
	@Value("${spring.application.name}")
    private String contextPath;
	
	@Value("${upload.dir}")
    private String uploadPath;
	
	@Autowired
	RegulationRepository regulationrepo;
	
	@Autowired
	RegulationHistoryRepo regulatehistrepo;

	@Autowired
	ActivityService actserv;
	
    // Format the date and time if necessary
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    LocalDate currentDate = null;
    LocalTime currentTime = null;
	
	private static Path getUniqueFilePath(Path targetLocation) {
		String filename = targetLocation.getFileName().toString();
        String fileExtension = "";
        
        System.err.println("Inside getUniqueFilePath() \n The filename is = "+filename+"\n TArget Location is = "+targetLocation.toString());
        
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0) {
            fileExtension = filename.substring(dotIndex);
            filename = filename.substring(0, dotIndex);
           
        }
        
        Path filePath = targetLocation;
        int count = 1;
        while (Files.exists(filePath)) {
            String newFilename = filename + "_" + count + fileExtension;
            filePath = targetLocation.getParent().resolve(newFilename);
            count++;
        }
        return filePath;
	} 
	
	@Override
	public Regulation saveRegulation(Regulation regulation,MultipartFile file) {
		
		 String filename = file.getOriginalFilename();
	     String filepath = "";  

	     File uploadDirectory = new File(uploadPath);
	     if(!uploadDirectory.exists())
	     {
	    	 boolean created = uploadDirectory.mkdirs();
    		 if(created) {
    			 
    			 File vendorDir = new File(uploadPath+File.separator+regulation.getVendor().getVendor_id() +File.separator+ regulation.getRegulationtype().getRegulation_type() );
    			 Path filePath = Paths.get(vendorDir.getAbsolutePath(),filename);
    			 
    			 filePath = getUniqueFilePath(filePath);
    			
    			 filename =filePath.getFileName().toString();// This will Get new file name if the file already exists
    			 filepath = vendorDir.getAbsolutePath();
    					 
    			 if(!vendorDir.exists()) {
    				 boolean vcreatedir = vendorDir.mkdirs();
    				 if(vcreatedir) {
    					 try(InputStream inputStream = file.getInputStream()) {
 							Files.copy(inputStream , filePath );
 						} catch (IOException e) { 
 							e.printStackTrace();
 						}
    				 }
    			 }
    			 else {
    				 try(InputStream inputStream = file.getInputStream()) {
							Files.copy(inputStream , filePath );
						} catch (IOException e) { 
							e.printStackTrace();
						}
    			 }
    		 }
	     }
	     else    {
	    	 File vendorDir = new File(uploadPath+File.separator+regulation.getVendor().getVendor_id() +File.separator+ regulation.getRegulationtype().getRegulation_type() );
	    	 filepath = vendorDir.getAbsolutePath();
	    	 
	    	 Path filePath =  Paths.get(vendorDir.getAbsolutePath(),filename);
	    	 
	    	 filePath = getUniqueFilePath(filePath); // This will Get new file name if the file already exists
			
			 filename =filePath.getFileName().toString();
			 
	    	 if(!vendorDir.exists()) {
				 boolean vcreatedir = vendorDir.mkdirs();
				 if(vcreatedir)
				 {
					 try(InputStream inputStream = file.getInputStream()) {
							Files.copy(inputStream , filePath );
						} catch (IOException e) {
							e.printStackTrace();
						}
				 }
			 }
			 else {
				 try(InputStream inputStream = file.getInputStream()) {
						Files.copy(inputStream , filePath );
					} catch (IOException e) {
						e.printStackTrace();
					}
			 }
	     }
	     
	     regulation.setFile_name(filename);
	     regulation.setFile_path(filepath );
	     
	     Regulation savedRegulation = regulationrepo.save(regulation);
	     
	     if(savedRegulation!=null) {
	    	 Activity activity = new Activity();
	    	 
	    	 // Get the current date
	         currentDate = LocalDate.now();
	         // Get the current time
	         currentTime = LocalTime.now();

	    	 activity.setActivity(regulation.getRegulation_name()+ " is saved successfully ");
	    	 activity.setActivity_date(currentDate.format(dateFormatter));
	    	 activity.setActivity_time(currentTime.format(timeFormatter));
	    	 actserv.saveActivity(activity);
	    	 
	     }
	     
	     RegulationHistory rhist = new RegulationHistory();
	     
	     rhist.setHist_file_name(filename);
	     rhist.setHist_file_path(filepath);
	     rhist.setHist_regulation_frequency(regulation.getRegulation_frequency());
	     rhist.setHist_regulation_description(regulation.getRegulation_description());
	     rhist.setHist_regulation_name(regulation.getRegulation_name());
	     rhist.setHist_regulation_issued_date(regulation.getRegulation_issued_date());
	     rhist.setVendor(regulation.getVendor());
	     rhist.setRegulation(regulation);
	     regulatehistrepo.save(rhist);
	     
		return savedRegulation;
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
	public int updateRegulation(Regulation regulation, MultipartFile file) {
		
		 String filename = file.getOriginalFilename();
	     String filepath = "";  

	     File uploadDirectory = new File(uploadPath);
	     if(!uploadDirectory.exists())
	     {
	    	 boolean created = uploadDirectory.mkdirs();
    		 if(created) {
    			 
    			 File vendorDir = new File(uploadPath+File.separator+regulation.getVendor().getVendor_id() +File.separator+ regulation.getRegulationtype().getRegulation_type() );
    			 Path filePath = Paths.get(vendorDir.getAbsolutePath(),filename);
    			 
    			 filePath = getUniqueFilePath(filePath);
    			
    			 filename =filePath.getFileName().toString();// This will Get new file name if the file already exists
    			 filepath = vendorDir.getAbsolutePath();
    					 
    			 if(!vendorDir.exists()) {
    				 boolean vcreatedir = vendorDir.mkdirs();
    				 if(vcreatedir) {
    					 try(InputStream inputStream = file.getInputStream()) {
 							Files.copy(inputStream , filePath );
 						} catch (IOException e) { 
 							e.printStackTrace();
 						}
    				 }
    			 }
    			 else {
    				 try(InputStream inputStream = file.getInputStream()) {
							Files.copy(inputStream , filePath );
						} catch (IOException e) { 
							e.printStackTrace();
						}
    			 }
    		 }
	     }
	     else    {
	    	 File vendorDir = new File(uploadPath+File.separator+regulation.getVendor().getVendor_id() +File.separator+ regulation.getRegulationtype().getRegulation_type() );
	    	 filepath = vendorDir.getAbsolutePath();
	    	 
	    	 Path filePath =  Paths.get(vendorDir.getAbsolutePath(),filename);
	    	 
	    	 filePath = getUniqueFilePath(filePath); // This will Get new file name if the file already exists
			
			 filename =filePath.getFileName().toString();
			 
	    	 if(!vendorDir.exists()) {
				 boolean vcreatedir = vendorDir.mkdirs();
				 if(vcreatedir)
				 {
					 try(InputStream inputStream = file.getInputStream()) {
							Files.copy(inputStream , filePath );
						} catch (IOException e) {
							e.printStackTrace();
						}
				 }
			 }
			 else {
				 try(InputStream inputStream = file.getInputStream()) {
						Files.copy(inputStream , filePath );
					} catch (IOException e) {
						e.printStackTrace();
					}
			 }
	     }
	     
	     regulation.setFile_name(filename);
	     regulation.setFile_path(filepath );
	     
	     int result = regulationrepo.updateRegulationById(regulation.getRegulation_name(), 
	    		 					  		regulation.getRegulation_description(), regulation.getRegulation_frequency(),
	    		 					  		regulation.getRegulation_issued_date(), filename, filepath , 
	    		 					  		regulation.getRegulationtype().getRegulation_type_id() ,
	    		 					  		regulation.getVendor().getVendor_id(), 
	    		 					  		regulation.getRegulation_id());
	     if(result > 0) {
	    	 Activity activity = new Activity();
	    	 
	    	 // Get the current date
	         currentDate = LocalDate.now();
	         // Get the current time
	         currentTime = LocalTime.now();

	    	 activity.setActivity(regulation.getRegulation_name()+ " is updated successfully ");
	    	 activity.setActivity_date(currentDate.format(dateFormatter));
	    	 activity.setActivity_time(currentTime.format(timeFormatter));
	    	 actserv.saveActivity(activity);
	    	 
	     }
	     
	     RegulationHistory rhist = new RegulationHistory();
	     
	     rhist.setHist_file_name(filename);
	     rhist.setHist_file_path(filepath);
	     rhist.setHist_regulation_frequency(regulation.getRegulation_frequency());
	     rhist.setHist_regulation_description(regulation.getRegulation_description());
	     rhist.setHist_regulation_name(regulation.getRegulation_name());
	     rhist.setHist_regulation_issued_date(regulation.getRegulation_issued_date());
	     rhist.setVendor(regulation.getVendor());
	     rhist.setRegulation(regulation);
	     regulatehistrepo.save(rhist);
		
		return result;
	}
	

	@Override
	public List<Regulation> getAllRegulationsByVendorId(Integer id) {
		
		return regulationrepo.getRegulationByVendorId(id);
	}

	@Override
	public List<Regulation> getRegulationsByVendorIdAndRegulationId(Integer vid, Integer rid) {
		return regulationrepo.getRegulationByVendorIdAndRegulationId(vid, rid);
	}

	@Override
	public List<Regulation> getExpiredRegulations() {
	
		
		return null;
	}

}
