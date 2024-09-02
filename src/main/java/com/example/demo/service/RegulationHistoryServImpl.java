package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.models.RegulationHistory;
import com.example.demo.repository.RegulationHistoryRepo;

@Service("reghistserv")
public class RegulationHistoryServImpl implements RegulationHistoryService {

	private RegulationHistoryRepo regulatehistrepo;
	
	public RegulationHistoryServImpl(RegulationHistoryRepo regulatehistrepo) {
		super();
		this.regulatehistrepo = regulatehistrepo;
	}

	@Override
	public List<RegulationHistory> getRegulationHistoryByRegulationId(Integer id) {
		
		List<RegulationHistory> reglist =
				regulatehistrepo.findAll()
									.stream()
									  .filter(reg-> {
											if(reg.getRegulation().getRegulation_id() ==id) {
												return true;
											} 
											else 
												return false;
									}).collect(Collectors.toList());
		return reglist;
	}

}
