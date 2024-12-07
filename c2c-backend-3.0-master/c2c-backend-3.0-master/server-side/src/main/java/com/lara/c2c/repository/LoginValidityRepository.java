package com.lara.c2c.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lara.c2c.model.LearnerLoginValidity;

public interface LoginValidityRepository extends JpaRepository<LearnerLoginValidity, Integer>{

}
