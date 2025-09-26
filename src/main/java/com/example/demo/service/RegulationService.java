package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.models.Regulation;
import com.example.demo.models.RegulationDTO;

public interface RegulationService {

	public Regulation saveRegulation(Regulation regulation, MultipartFile file);
	
	public List<RegulationDTO> getAllRegulationDtos();
	
	public List<Regulation> getAllRegulations();
	
	public Regulation getRegulationById(Integer id);
	
	public int updateRegulation(Regulation regulation, MultipartFile file);
	
	public List<Regulation> getAllRegulationsByVendorId(Integer id);
	
	public List<Regulation> getRegulationsByVendorIdAndRegulationId(Integer vid, Integer rid);
	
	public List<Regulation> getExpiredRegulations();
	
	public List<Regulation> getExpiredRegulationsByVendorId(Integer vendorid);
}
