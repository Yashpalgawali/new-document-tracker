package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exceptions.GlobalException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.UserType;
import com.example.demo.repository.UserTypeRepository;

@Service("usertypeserv")
public class UserTypeServImpl implements IUserTypeService {

	private final UserTypeRepository usertyperepo;
	
	public UserTypeServImpl(UserTypeRepository usertyperepo) {
		super();
		this.usertyperepo = usertyperepo;
	}

	@Override
	public UserType saveUserType(UserType usertype) {
		UserType userType = usertyperepo.save(usertype);
		if(userType!=null) {
			return userType;
		}
		throw new GlobalException("User Type "+usertype.getUser_type()+" is not saved");
	}

	@Override
	public List<UserType> getAllUserTypes() {
		List<UserType> userTypeList = usertyperepo.findAll();
		if(!userTypeList.isEmpty()) {
			return userTypeList;
		}
		throw new ResourceNotFoundException("No User Types Found");
	}

	@Override
	public UserType getUserTypeById(Integer usertypeid) {
		
		return usertyperepo.findById(usertypeid).orElseThrow(() -> new ResourceNotFoundException("No User Type found for given ID "+usertypeid));
	}

	@Override
	public int updateUserType(UserType usertype) {
		// TODO Auto-generated method stub
		return 0;
	}

}
