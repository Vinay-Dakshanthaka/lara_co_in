package com.lara.c2c.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lara.c2c.model.TimeSpentOnDocument;

public interface TimeSpentDocRepository extends JpaRepository<TimeSpentOnDocument, Integer>{

}
