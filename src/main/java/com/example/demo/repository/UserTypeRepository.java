package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.UserType;

@Repository("usertyperepo")
public interface UserTypeRepository extends JpaRepository<UserType, Integer> {

}
