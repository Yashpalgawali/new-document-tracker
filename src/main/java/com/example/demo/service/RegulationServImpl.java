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
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exceptions.GlobalException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.global.GlobalVars;
import com.example.demo.models.Activity;
import com.example.demo.models.Regulation;
import com.example.demo.models.RegulationDTO;
import com.example.demo.models.RegulationHistory;
import com.example.demo.repository.RegulationHistoryRepo;
import com.example.demo.repository.RegulationRepository;

@Service("regulationserv")
public class RegulationServImpl implements RegulationService {
	
	@Value("${spring.application.name}")
    private String contextPath;
	
	@Value("${upload.dir}")
    private String uploadPath;
	
	private final RegulationRepository regulationrepo;
	
	private final RegulationHistoryRepo regulatehistrepo;

	private final ActivityService actserv;
	
	public RegulationServImpl(RegulationRepository regulationrepo, RegulationHistoryRepo regulatehistrepo,
			ActivityService actserv) {
		super();
		this.regulationrepo = regulationrepo;
		this.regulatehistrepo = regulatehistrepo;
		this.actserv = actserv;
	}

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

	    	 RegulationHistory rhist = new RegulationHistory();

		     rhist.setHist_file_name(filename);
		     rhist.setHist_file_path(filepath);
		     rhist.setHist_regulation_frequency(regulation.getRegulation_frequency());
		     rhist.setHist_regulation_description(regulation.getRegulation_description());
		     rhist.setHist_regulation_name(regulation.getRegulation_name());
		     rhist.setHist_regulation_issued_date(regulation.getRegulation_issued_date());
		     rhist.setHist_next_renewal_date(regulation.getNext_renewal_date());
		     rhist.setVendor(regulation.getVendor());
		     rhist.setRegulation(regulation);
		     regulatehistrepo.save(rhist);
	     }

		return savedRegulation;
	}
	

	@Override
	public List<RegulationDTO> getAllRegulationDtos() {
		List<Regulation> regList = regulationrepo.findAll();
		
		if(regList.size()> 0 ) {
			List<RegulationDTO> regulationDTOs = new ArrayList<>();
			
			for (Regulation regulation : regList) {
				RegulationDTO rdto = new RegulationDTO();
				rdto.setRegulation_id(regulation.getRegulation_id());
				rdto.setRegulation_name(regulation.getRegulation_name());
				rdto.setRegulation_description(regulation.getRegulation_description());
				rdto.setRegulation_frequency(regulation.getRegulation_frequency());
				rdto.setRegulation_issued_date(regulation.getRegulation_issued_date());
				rdto.setNext_renewal_date(regulation.getNext_renewal_date());
				rdto.setFile_name(regulation.getFile_name());
				rdto.setFile_path(regulation.getFile_path());
				rdto.setVendor(regulation.getVendor());
				rdto.setRegulation_type(regulation.getRegulationtype());
	
				regulationDTOs.add(rdto);
	
			}
			return regulationDTOs;
		}
		throw new ResourceNotFoundException("No Regulations found");
		
	}
	
	@Override
	public List<Regulation> getAllRegulations() {
		List<Regulation> regList = regulationrepo.findAll();
		
		if(regList.size()> 0 ) {			 
			return regList;
		}
		throw new GlobalException("No Regulations found");
		
	}

	@Override
	public Regulation getRegulationById(Integer id) {
		return  regulationrepo.findById(id).orElseThrow(()->new ResourceNotFoundException("No regulation found for given ID "+id));		
	}

	
	@Override
	public int updateRegulation(Regulation regulation, MultipartFile file) {
		
		System.err.println("Inside UPDATE REGULATION \n");
		 String filename = file.getOriginalFilename();
	     String filepath = "";  

	     File uploadDirectory = new File(uploadPath);
	     if(!uploadDirectory.exists()) {
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
	     else {
	    	 File vendorDir = new File(uploadPath+File.separator+regulation.getVendor().getVendor_id() +File.separator+ regulation.getRegulationtype().getRegulation_type() );
	    	 filepath = vendorDir.getAbsolutePath();
	    	 
	    	 Path filePath =  Paths.get(vendorDir.getAbsolutePath(),filename);
	    	 filePath = getUniqueFilePath(filePath); // This will Get new file name if the file already exists
	    	 filename =filePath.getFileName().toString();
			 
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
	     
	     regulation.setFile_name(filename);
	     regulation.setFile_path(filepath );
	     
	     int result = regulationrepo.updateRegulationById(regulation.getRegulation_name(), 
	    		 					  		regulation.getRegulation_description(), regulation.getRegulation_frequency(),
	    		 					  		regulation.getRegulation_issued_date(), filename, filepath , 
	    		 					  		regulation.getRegulationtype().getRegulation_type_id() ,
	    		 					  		regulation.getVendor().getVendor_id(), 
	    		 					  		regulation.getRegulation_id(),regulation.getNext_renewal_date());
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
	     rhist.setHist_next_renewal_date(regulation.getNext_renewal_date());
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

		List<Regulation> reglist = regulationrepo.findAll();

        // Current date for comparison
        LocalDate today = LocalDate.now();

        List<Regulation> explist = reglist.stream().filter(reg->{

				 // Parse the next_renewal_date to LocalDate using the custom formatter
	            LocalDate entryDate = LocalDate.parse(reg.getNext_renewal_date(), dateFormatter);
	            // Check if the date is before today
	            return entryDate.isBefore(today);

				}).collect(Collectors.toList());
		return explist;
	}

	@Override
	public List<Regulation> getExpiredRegulationsByVendorId(Integer vendorid) {
		List<Regulation> reglist = regulationrepo.findAll();
        
        // Current date for comparison
        LocalDate today = LocalDate.now();
 
        List<Regulation> explist = reglist.stream().filter(reg->{
				 
        		if(reg.getVendor().getVendor_id()==vendorid) 
        		{
					 // Parse the next_renewal_date to LocalDate using the custom formatter
		            LocalDate entryDate = LocalDate.parse(reg.getNext_renewal_date(), dateFormatter);
		            // Check if the date is before today
		            return entryDate.isBefore(today);
        		}
        		else {
        			return false;
        		}
				}).collect(Collectors.toList());
		return explist;
	}

}
