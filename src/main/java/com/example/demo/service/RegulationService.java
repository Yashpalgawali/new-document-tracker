package com.example.demo.service;

import java.util.List;

import com.example.demo.models.Regulation;

public interface RegulationService {

	public Regulation saveRegulation(Regulation regulation);
	
	public List<Regulation> getAllRegulations();
	
	public Regulation getRegualtionById(Integer id);
	
	public int updateRegulation(Regulation regulation);
	
	public List<Regulation> getAllRegulationsByVendorId(Integer id);
	
	public List<Regulation> getRegulationsByVendorIdAndRegulationId(Integer vid, Integer rid);
}
