package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.repository.RegulationHistoryRepo;

@Service("reghistserv")
public class RegulationHistoryServImpl implements RegulationHistoryService {

	private RegulationHistoryRepo regulatehistrepo;
	
	public RegulationHistoryServImpl(RegulationHistoryRepo regulatehistrepo) {
		super();
		this.regulatehistrepo = regulatehistrepo;
	}


	@Override
	public List<com.example.demo.models.RegulationHistory> getRegulationHistoryByRegulationId(Integer id) {
		// TODO Auto-generated method stub
		List<com.example.demo.models.RegulationHistory> reglist = regulatehistrepo.findAll().stream().filter(reg-> {
													if(reg.getHist_regulation_id()==id) {
														return true;
													} 
													else 
														return false;
											}).collect(Collectors.toList());
		reglist.stream().forEach(System.err::println);
		
		return  reglist;
	}

}
