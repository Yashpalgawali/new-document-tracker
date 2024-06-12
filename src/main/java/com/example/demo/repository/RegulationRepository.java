package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Regulation;

@Repository("regulationrepo")
public interface RegulationRepository extends JpaRepository<Regulation, Integer> {

}
