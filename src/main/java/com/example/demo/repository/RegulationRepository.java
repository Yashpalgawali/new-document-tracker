package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Regulation;

@Repository("regulationrepo")
public interface RegulationRepository extends JpaRepository<Regulation, Integer> {
	
	
	@Modifying
	@Transactional
	@Query("UPDATE Regulation r SET "
			+ "r.regulation_name=:rname,r.regulation_description=:rdesc,r.regulation_frequency=:freq,r.regulation_issued_date=:date"
			+ ",r.file_path=:fpath,r.file_name=:fname,r.regulationtype.regulation_type_id=:rtypeid,r.vendor.vendor_id=:vendid WHERE r.regulation_id=:regid")
	public int updateRegulationById(String rname,String rdesc,String freq,String date,String fname,String fpath,Integer rtypeid,Integer vendid,Integer regid);
	
	
	@Query("SELECT r FROM Regulation r WHERE r.vendor.vendor_id=:vendor_id")
	public List<Regulation> getRegulationByVendorId(Integer vendor_id);
	
	
	
	@Query("SELECT r FROM Regulation r WHERE r.vendor.vendor_id=:vendor_id AND r.regulation_id=:reg_id")
	public List<Regulation> getRegulationByVendorIdAndRegulationId(Integer vendor_id,Integer reg_id);
	
	
}
