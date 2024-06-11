package com.example.demo.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.models.RegulationType;

@Repository("regulationtyperepo")
public interface RegulationTypeRepository extends JpaRepository<RegulationType, Integer> {

	@Modifying
	@Transactional
	@Query("UPDATE RegulationType r set r.regulation_type=:regtype WHERE r.regulation_type_id=:id")
	public int updateregulationType(Integer id, String regtype);
} 