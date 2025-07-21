package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.User;
import com.example.demo.models.Vendor;

@Repository("vendrepo")
public interface VendorRepository extends JpaRepository<Vendor, Integer> {

	
	public Optional<Vendor> findByUser(User user);
	
	@Transactional
	@Modifying
	@Query("UPDATE Vendor v SET v.vendor_name=:vendor_name,v.vendor_email=:vendor_email,v.user.userid=:user_id,v.enabled=:enabled WHERE v.vendor_id=:vid")
	public int updateVendor(String vendor_name,String vendor_email,Long user_id,Integer enabled,Integer vid);
}
