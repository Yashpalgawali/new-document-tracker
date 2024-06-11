package com.example.demo.service;

import java.util.List;

import com.example.demo.models.RegulationType;

public interface RegulationTypeService {

	public RegulationType saveRegulationType(RegulationType rtype);
	
	public List<RegulationType> getAllRegulationTypes();
	
	public RegulationType getRegulationTypeById(Integer id);
	
	public int updateRegulationType(RegulationType rtype);
}
