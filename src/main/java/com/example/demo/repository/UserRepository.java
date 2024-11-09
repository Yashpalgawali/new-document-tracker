package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.User;

@Repository("userrepo")
public interface UserRepository extends JpaRepository<User, Integer> {

	
	public User findByUsername(String username);
	
	@Query("UPDATE User u SET u.password=:pass WHERE userid=:id")
	@Transactional
	@Modifying
	public int updateUserPassword(String pass,Integer id);
}