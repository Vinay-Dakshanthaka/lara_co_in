package com.lara.c2c.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lara.c2c.model.ClassRecord;



public interface ClassRecordRepository extends JpaRepository<ClassRecord, Integer>{
   
	@Query(value = "SELECT * FROM class_record cr WHERE DATE_FORMAT(cr.date, '%Y-%m-%d') = (SELECT MAX(DATE_FORMAT(cr2.date, '%Y-%m-%d')) FROM class_record cr2)", nativeQuery = true)
	public List<ClassRecord> getSelectAll();
}
