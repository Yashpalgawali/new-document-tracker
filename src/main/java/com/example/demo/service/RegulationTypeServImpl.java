package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.global.GlobalVars;
import com.example.demo.models.Activity;
import com.example.demo.models.RegulationType;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.RegulationTypeRepository;

@Service("regulationtypeserv")
public class RegulationTypeServImpl implements RegulationTypeService {
	
	private RegulationTypeRepository regulationtyperepo;
	
	private ActivityRepository actrepo;
	
	@Autowired
	public RegulationTypeServImpl(RegulationTypeRepository regulationtyperepo, ActivityRepository actrepo) {
		super();
		this.regulationtyperepo = regulationtyperepo;
		this.actrepo = actrepo;
	}

	@Override
	public RegulationType saveRegulationType(RegulationType rtype) {
	 
		RegulationType rtp = regulationtyperepo.save(rtype);
		if(rtp!=null) {
			Activity act = new Activity();
			 
			act.setActivity("Regulation Type "+ rtype.getRegulation_type() +" is saved successfully");
			act.setActivity_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()) );
			act.setActivity_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()) );
			
			actrepo.save(act);
			return rtp;
		}
		else {
			Activity act = new Activity();
			act.setActivity("Regulation Type "+ rtype.getRegulation_type() +" is not saved ");
			act.setActivity_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()) );
			act.setActivity_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()) );
			
			actrepo.save(act);
			return rtp;
		}
		
	}

	@Override
	public List<RegulationType> getAllRegulationTypes() {
		return regulationtyperepo.findAll();
	}

	@Override
	public RegulationType getRegulationTypeById(Integer id) {
		Optional<RegulationType> regtype = regulationtyperepo.findById(id);
		if(regtype.isPresent()) {
			return regulationtyperepo.findById(id).get();
		}
		else {
			return null;
		}
	}

	@Override
	public int updateRegulationType(RegulationType rtype) {
		int res = regulationtyperepo.updateregulationType(rtype.getRegulation_type_id() , rtype.getRegulation_type());
		if(res>0) {
			Activity act = new Activity();
			 
			act.setActivity("Regulation Type "+ rtype.getRegulation_type() +" is updated successfully");
			act.setActivity_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()) );
			act.setActivity_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()) );
			
			actrepo.save(act);
			return res;
		}
		else {
			Activity act = new Activity();
			 
			act.setActivity("Regulation Type "+ rtype.getRegulation_type() +" is not updated ");
			act.setActivity_date(GlobalVars.DATE_FORMAT.format(LocalDateTime.now()) );
			act.setActivity_time(GlobalVars.TIME_FORMAT.format(LocalDateTime.now()) );
			
			actrepo.save(act);
			return res;
		}
	}

}
