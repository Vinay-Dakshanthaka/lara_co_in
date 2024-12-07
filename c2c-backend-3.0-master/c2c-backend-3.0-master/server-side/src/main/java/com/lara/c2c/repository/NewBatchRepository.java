package com.lara.c2c.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lara.c2c.model.NewBatch;

public interface NewBatchRepository extends JpaRepository<NewBatch, Integer>
{
	
}
