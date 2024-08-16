package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.models.Regulation;

public interface RegulationService {

	public Regulation saveRegulation(Regulation regulation, MultipartFile file);
	
	public List<Regulation> getAllRegulations();
	
	public Regulation getRegualtionById(Integer id);
	
	public int updateRegulation(Regulation regulation, MultipartFile file);
	
	public List<Regulation> getAllRegulationsByVendorId(Integer id);
	
	public List<Regulation> getRegulationsByVendorIdAndRegulationId(Integer vid, Integer rid);
	
	public List<Regulation> getExpiredRegulations();
}
