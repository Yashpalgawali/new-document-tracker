package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

	private Path getUniqueFilePath(Path targetLocation) {
		String filename = targetLocation.getFileName().toString();
        String fileExtension = "";
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
		
		 System.err.println("Inside saveregulation() \n "+regulation.toString()+"\n");
		 String filename = file.getOriginalFilename();
	     String filepath = "";  

	     File uploadDirectory = new File(uploadPath);
	     if(!uploadDirectory.exists())
	     {
	    	 System.err.println("upload directory not present ");
	    	 boolean created = uploadDirectory.mkdirs();
    		 if(created)
    		 {
    			 System.err.println("Upload directory created");
    			 File vendorDir = new File(uploadPath+File.separator+regulation.getVendor().getVendor_id() +File.separator+ regulation.getRegulationtype().getRegulation_type() );
    			 Path filePath = Paths.get(vendorDir.getAbsolutePath());
    			 
    			 filepath = vendorDir.getAbsolutePath();
    					 
    			 if(!vendorDir.exists()) {
    				 boolean vcreatedir = vendorDir.mkdirs();
    				 if(vcreatedir) {
    					 System.err.println("Vendor Directory is created \n i.e. "+vendorDir);
    				 }
    			 }
    			 else {
    				 System.err.println("Vendor Directory already present \n i.e. "+vendorDir);
    			 }
    		 }
	     }
	     else 
	     {
	    	 File vendorDir = new File(uploadPath+File.separator+regulation.getVendor().getVendor_id() +File.separator+ regulation.getRegulationtype().getRegulation_type() );
	    	 filepath = vendorDir.getAbsolutePath();
	    	 
	    	 Path filePath =  Paths.get(vendorDir.getAbsolutePath(),file.getName());
			 filePath = getUniqueFilePath(filePath);
			 
	    	 if(!vendorDir.exists()) {
				 boolean vcreatedir = vendorDir.mkdirs();
				 if(vcreatedir)
				 {
					 System.err.println("Vendor Directory is created \n i.e. "+vendorDir);
					 try(InputStream inputStream = file.getInputStream()) {
							Files.copy(inputStream , filePath );
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				 }
			 }
			 else {
				 System.err.println("Vendor Directory already present \n i.e. "+vendorDir);
				 try(InputStream inputStream = file.getInputStream()) {
						Files.copy(inputStream , filePath );
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 }
	    	 
	     }
	     
	     regulation.setFile_name(filename);
	     regulation.setFile_path(filepath );
	     
	     Regulation savedRegulation = regulationrepo.save(regulation);
	     
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
	
//	@Override
//	public Regulation saveRegulation(Regulation regulation,MultipartFile file) {
//		
//		System.err.println("Inside saveregulation() \n "+regulation.toString()+"\n");
//		 String filename = file.getOriginalFilename();
//	     String filepath = "";  
//	     
//	     if (file != null) {
//	    	 File uploadDirectory = new File(uploadPath);
//	    	 if(!uploadDirectory.exists())
//	    	 {
//	    		 boolean created = uploadDirectory.mkdirs();
//	    		 if(created)
//	    		 {
//	    			 File vendorDir = new File(uploadPath+File.separator+regulation.getVendor().getVendor_id() +File.separator+ regulation.getRegulationtype().getRegulation_type() );
//	    			 filepath =  vendorDir.getAbsolutePath();
//	    			 if(!vendorDir.exists())  {
//	    				 boolean create = vendorDir.mkdirs();
//	    				 if(create) {
//	    					 
//	    					 Path filePath = Paths.get(vendorDir.getAbsolutePath(), filename);
//	    					 try {
//								Files.copy(file.getInputStream(), filePath);
//								
//								InputStream ipstream = file.getInputStream();
//								ipstream.close();
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//	    				 }
//	    			 }
//	    			 else {
//	    				 Path filePath = Paths.get(vendorDir.getAbsolutePath(), filename);
//    					 try {
//							Files.copy(file.getInputStream(), filePath);
//							
//							InputStream ipstream = file.getInputStream();
//							ipstream.close();
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//	    			 }
//	    		 }
//	    	 }
//	    	 else {
//	    		 
//	    		 File vendorDir = new File(uploadPath+File.separator+regulation.getVendor().getVendor_id()+File.separator+ regulation.getRegulationtype().getRegulation_type() );
//	    		 filepath =  vendorDir.getAbsolutePath();
//    			 if(!vendorDir.exists()) {
//    				 System.err.println("Vendor Directory \n"+filepath+" DOES NOT EXISTS \n");
//    				 boolean create = vendorDir.mkdirs();
//    				 if(create) {
//    					 
//    					 Path filePath = Paths.get(filepath, filename);
//    					 try {
//							Files.copy(file.getInputStream(), filePath);
//						   
//							InputStream ipstream = file.getInputStream();
//							ipstream.close();
//						} catch (IOException e) {
//
//							e.printStackTrace();
//						}
//    				 }
//    			 }
//    			 else {
//    				 System.err.println("Vendor Directory \n"+filepath+" already EXISTS\nFile name is "+filename+"\n");
//    				 Path filePath = Paths.get(filepath, filename);
//					 try {
//						 File newFile = new File(filePath.toString() +File.separator +filename);
//						
//						 	if(!Files.exists(filePath))
//						 	{
//						 		 Path targetLocation = Paths.get(uploadPath).resolve(file.getOriginalFilename());
//
//						         // Ensure the directories exist
//						         Files.createDirectories(targetLocation.getParent());
//
//						         // Copy the file to the target location
//						         try (InputStream inputStream = file.getInputStream()) {
//						             Files.copy(inputStream, targetLocation);
//						         }
//
//						 	}
//						 	else {
//						 		
//						 		uploadPath = uploadPath+File.separator+5+regulation.getRegulationtype().getRegulation_type();
//						 		Path targetLocation = Paths.get(uploadPath).resolve(file.getOriginalFilename());
//
//						         // Ensure the directories exist
//						         Files.createDirectories(targetLocation.getParent());
//
//						         // Copy the file to the target location
//						         try (InputStream inputStream = file.getInputStream()) {
//						        	 
//						        	 String baseName = targetLocation.getFileName().toString();
//						        	 String extension = "";
//						        	 
//						        	 int dotIndex = baseName.lastIndexOf(".");
//						        	 if(dotIndex > 0)  {
//						        		 extension = baseName.substring(dotIndex);
//						        		 baseName = baseName.substring(0, dotIndex);
//						        	 }
//						        	 
//						        	 filePath = targetLocation;
//						        	 int count = 1;
//						        	 
//						        	 while(Files.exists(filePath)) {
//						        		 String newFileName = baseName+"_"+count+extension;
//						        		 filePath = targetLocation.getParent().resolve(newFileName);
//						        		 count++;
//						        	 }
//						        	 
//						             Files.copy(inputStream, filePath);
//						         }
//						 	}
//
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//    			 }
//	     }
//	} 
//	     
//	     regulation.setFile_name(filename);
//	     regulation.setFile_path(filepath);
//	     
//	     Regulation savedRegulation = regulationrepo.save(regulation);
//	     
//	     RegulationHistory rhist = new RegulationHistory();
//	     
//	     rhist.setHist_file_name(filename);
//	     rhist.setHist_file_path(filepath);
//	     rhist.setHist_regulation_frequency(regulation.getRegulation_frequency());
//	     rhist.setHist_regulation_description(regulation.getRegulation_description());
//	     rhist.setHist_regulation_name(regulation.getRegulation_name());
//	     rhist.setHist_regulation_issued_date(regulation.getRegulation_issued_date());
//	     rhist.setVendor(regulation.getVendor());
//	     rhist.setRegulation(regulation);
//	     regulatehistrepo.save(rhist);
//	     
//		return savedRegulation;
//	}

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
		System.err.println("Inside updateRegulation() \n "+regulation.toString()+"\n");
		 String filename = file.getOriginalFilename();
	     String filepath = "";  
	     
	     if (file != null) {
	    	 File uploadDirectory = new File(uploadPath);
	    	 if(!uploadDirectory.exists())
	    	 {
	    		 boolean created = uploadDirectory.mkdirs();
	    		 if(created)
	    		 {
	    			 File vendorDir = new File(uploadPath+File.separator+regulation.getVendor().getVendor_id() +File.separator+ regulation.getRegulationtype().getRegulation_type() );
	    			 filepath =  vendorDir.getAbsolutePath();
	    			 if(!vendorDir.exists()) 
	    			 {
	    				 boolean create = vendorDir.mkdirs();
	    				 if(create) {
	    					 
	    					 Path filePath = Paths.get(vendorDir.getAbsolutePath(), filename);
	    					 
	    					 try {
//	    						 	File newFile = new File(filepath+filename);
//	    						 	if(!newFile.exists())
//	    						 	{
//	    						 		Files.copy(file.getInputStream(), filePath);
//	    						 		InputStream ipstream = file.getInputStream();
//	    						 		ipstream.close();
//	    						 	}
//	    						 	else {
//	    						 		 String baseName = filename;
//	    						         String extension = "";
//	    						         int dotIndex = filename.lastIndexOf('.');
//	    						         if (dotIndex > 0) {
//	    						             baseName = filename.substring(0, dotIndex);
//	    						             extension = filename.substring(dotIndex);
//	    						         }
//	    						         int count = 1;
//	    						         while (newFile.exists()) {
//	    						             String newFileName = baseName + count + extension;
//	    						             newFile = new File(newFileName);
//	    						             count++;
//	    						             filePath = Paths.get(vendorDir.getAbsolutePath(), newFileName);
//	    						         }
	    						         System.err.println("inside vendor directory create() \n");
	    						         Files.copy(file.getInputStream(), filePath);
	    						         Files.deleteIfExists(filePath);
		    						 		InputStream ipstream = file.getInputStream();
		    						 		ipstream.close();
	    						 	//}
	    				        
							} catch (IOException e) {
								e.printStackTrace();
							}
	    				 }
	    			 }
	    			 else {
	    				 Path filePath = Paths.get(vendorDir.getAbsolutePath(), filename);
   					 try {
//   						File newFile = new File(filepath+filename);
//					 	if(!newFile.exists())
//					 	{
//					 		Files.copy(file.getInputStream(), filePath);
//					 		InputStream ipstream = file.getInputStream();
//					 		ipstream.close();
//					 	}
//					 	else {
//					 		 String baseName = filename;
//					         String extension = "";
//					         int dotIndex = filename.lastIndexOf('.');
//					         if (dotIndex > 0) {
//					             baseName = filename.substring(0, dotIndex);
//					             extension = filename.substring(dotIndex);
//					         }
//					         int count = 1;
//					         while (newFile.exists()) {
//					             String newFileName = baseName + count + extension;
//					             newFile = new File(newFileName);
//					             count++;
//					             filePath = Paths.get(vendorDir.getAbsolutePath(), newFileName);
//					         }
   						 System.err.println("Inside alredy created vendors Directory\n");
					         Files.copy(file.getInputStream(), filePath);
					         Files.deleteIfExists(filePath);
						 	 InputStream ipstream = file.getInputStream();
						 	 ipstream.close();
					 	//}
						} catch (IOException e) {
							e.printStackTrace();
						}
	    			 }
	    		 }
	    	 }
	    	 else {
	    		 System.err.println("Upload directory alredy exists \n");
	    		 File vendorDir = new File(uploadPath+File.separator+regulation.getVendor().getVendor_id()+File.separator+ regulation.getRegulationtype().getRegulation_type() );
	    		 filepath =  vendorDir.getAbsolutePath();
   			 if(!vendorDir.exists()) {
   				 boolean create = vendorDir.mkdirs();
   				 if(create) {
   					 
   					 Path filePath = Paths.get(vendorDir.getAbsolutePath(), filename);
   					 try {
//   						File newFile = new File(filepath+filename);
//					 	if(!newFile.exists())
//					 	{
//					 		Files.copy(file.getInputStream(), filePath);
//					 		InputStream ipstream = file.getInputStream();
//					 		ipstream.close();
//					 	}
//					 	else {
//					 		 String baseName = filename;
//					         String extension = "";
//					         int dotIndex = filename.lastIndexOf('.');
//					         if (dotIndex > 0) {
//					             baseName = filename.substring(0, dotIndex);
//					             extension = filename.substring(dotIndex);
//					         }
//					         int count = 1;
//					         while (newFile.exists()) {
//					             String newFileName = baseName + count + extension;
//					             newFile = new File(newFileName);
//					             count++;
//					             filePath = Paths.get(vendorDir.getAbsolutePath(), newFileName);
//					         }
//					         Files.copy(file.getInputStream(), filePath);
//						 		InputStream ipstream = file.getInputStream();
//						 		ipstream.close();
//					 	}
							Files.copy(file.getInputStream(), filePath);
							   Files.deleteIfExists(filePath);
							InputStream ipstream = file.getInputStream();
							ipstream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
   				 }
   			 }
   			 else {
   				 System.err.println("Inside the else block of updateregulation \n ");
   				 Path filePath = Paths.get(vendorDir.getAbsolutePath(), filename);
					 try {
					
						 File newFile = new File(vendorDir.getAbsolutePath()+filename);
						 	if(!newFile.exists())
						 	{
						 		Files.copy(file.getInputStream(), filePath);
						 		InputStream ipstream = file.getInputStream();
						 		ipstream.close();
						 	}
						 	else {
						 		 String baseName = filename;
						         String extension = "";
						         int dotIndex = filename.lastIndexOf('.');
						         if (dotIndex > 0) {
						             baseName = filename.substring(0, dotIndex);
						             extension = filename.substring(dotIndex);
						         }
						         int count = 1;
						         while (newFile.exists()) {
						             String newFileName = baseName + count + extension;
						             newFile = new File(newFileName);
						             count++;
						             filePath = Paths.get(vendorDir.getAbsolutePath(), newFileName);
						         }
						        Files.copy(file.getInputStream(), filePath);
 						 		InputStream ipstream = file.getInputStream();
 						 		ipstream.close();
						 	}

					} catch (IOException e) {
						e.printStackTrace();
					}
   			 }
	     }
	} 
	     regulation.setFile_name(filename);
	     regulation.setFile_path(filepath);
	      
	     RegulationHistory rhist = new RegulationHistory();
		     
	     rhist.setHist_file_name(filename);
	     rhist.setHist_file_path(filepath);
	     rhist.setHist_regulation_description(regulation.getRegulation_description());
	     rhist.setHist_regulation_name(regulation.getRegulation_name());
	     rhist.setHist_regulation_issued_date(regulation.getRegulation_issued_date());
	     rhist.setVendor(regulation.getVendor());
	     rhist.setRegulation(regulation);
	     regulatehistrepo.save(rhist);
		     
		System.err.println("\n After uploading file to server data is \n "+regulation.toString()+"\n");
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
