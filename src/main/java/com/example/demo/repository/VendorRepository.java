package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.User;
import com.example.demo.models.Vendor;

@Repository("vendrepo")
public interface VendorRepository extends JpaRepository<Vendor, Integer> {

	
	public Vendor findByUser(User user);
}
