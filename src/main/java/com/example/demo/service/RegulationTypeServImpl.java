package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exceptions.GlobalException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.global.GlobalVars;
import com.example.demo.models.Activity;
import com.example.demo.models.RegulationType;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.RegulationTypeRepository;

@Service("regulationtypeserv")
public class RegulationTypeServImpl implements RegulationTypeService {

	private final RegulationTypeRepository regulationtyperepo;

	private final ActivityRepository actrepo;

	public RegulationTypeServImpl(RegulationTypeRepository regulationtyperepo, ActivityRepository actrepo) {
		super();
		this.regulationtyperepo = regulationtyperepo;
		this.actrepo = actrepo;
	}

	@Override
	public RegulationType saveRegulationType(RegulationType rtype) {

		RegulationType rtp = regulationtyperepo.save(rtype);
		if (rtp != null) {
			Activity act = new Activity();

			act.setActivity("Regulation Type " + rtype.getRegulation_type() + " is saved successfully");
			act.setActivity_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()));
			act.setActivity_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()));

			actrepo.save(act);
			return rtp;
		} else {
			Activity act = new Activity();
			act.setActivity("Regulation Type " + rtype.getRegulation_type() + " is not saved ");
			act.setActivity_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()));
			act.setActivity_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()));

			actrepo.save(act);
			throw new GlobalException("Regulation Type " + rtype.getRegulation_type() + " is not saved");
		}
	}

	@Override
	public List<RegulationType> getAllRegulationTypes() {
		List<RegulationType> regtypeList = regulationtyperepo.findAll();
		if (regtypeList.size() > 0)
			return regtypeList;
		throw new ResourceNotFoundException("No Regulation Types found");
	}

	@Override
	public RegulationType getRegulationTypeById(Integer id) {
		return regulationtyperepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Regulation Type found for given ID " + id));
	}

	@Override
	public int updateRegulationType(RegulationType rtype) {
		
		System.err.println("To be updated "+rtype.toString());
		
		int res = regulationtyperepo.updateregulationType(rtype.getRegulation_type_id(), rtype.getRegulation_type());
		
		RegulationType regtype = regulationtyperepo.save(rtype);
		
		if (regtype!=null ) {
			Activity act = new Activity();

			act.setActivity("Regulation Type " + rtype.getRegulation_type() + " is updated successfully");
			act.setActivity_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()));
			act.setActivity_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()));

			actrepo.save(act);
			return res;
			
		} 
		else {
			Activity act = new Activity();

			act.setActivity("Regulation Type " + rtype.getRegulation_type() + " is not updated ");
			act.setActivity_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()));
			act.setActivity_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()));

			actrepo.save(act);
			throw new GlobalException("Regulation Type " + rtype.getRegulation_type() + " is NOT Updated");
		}
	}

}
