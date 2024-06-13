package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Regulation;
import com.example.demo.repository.RegulationRepository;

@Service("regulationserv")
public class RegulationServImpl implements RegulationService {

	@Autowired
	RegulationRepository regulationrepo;
	
	@Override
	public Regulation saveRegulation(Regulation regulation) {
		return regulationrepo.save(regulation);
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
	public int updateRegulation(Regulation regulation) {
		// TODO Auto-generated method stub
		return 1;
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
