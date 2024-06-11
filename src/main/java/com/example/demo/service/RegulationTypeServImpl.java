package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.RegulationType;
import com.example.demo.repository.RegulationTypeRepository;

@Service("regulationtypeserv")
public class RegulationTypeServImpl implements RegulationTypeService {

	@Autowired
	RegulationTypeRepository regulationtyperepo;
	
	@Override
	public RegulationType saveRegulationType(RegulationType rtype) {
		// TODO Auto-generated method stub
		return regulationtyperepo.save(rtype);
	}

	@Override
	public List<RegulationType> getAllRegulationTypes() {
		// TODO Auto-generated method stub
		return regulationtyperepo.findAll();
	}

	@Override
	public RegulationType getRegulationTypeById(Integer id) {
		Optional<RegulationType> regtype = regulationtyperepo.findById(id);
		if(regtype.isPresent())
		{
			return regulationtyperepo.findById(id).get();
		}
		else {
			return null;
		}
		
	}

	@Override
	public int updateRegulationType(RegulationType rtype) {
		int res = regulationtyperepo.updateregulationType(rtype.getRegulation_type_id() , rtype.getRegulation_type());
		return res;
	}

}
