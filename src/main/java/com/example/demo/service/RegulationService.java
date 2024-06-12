package com.example.demo.service;

import java.util.List;

import com.example.demo.models.Regulation;

public interface RegulationService {

	public Regulation saveRegulation(Regulation regulation);
	
	public List<Regulation> getAllRegulations();
	
	public Regulation getRegualtionById(Integer id);
	
	public Regulation updateRegulation(Regulation regulation);
}
