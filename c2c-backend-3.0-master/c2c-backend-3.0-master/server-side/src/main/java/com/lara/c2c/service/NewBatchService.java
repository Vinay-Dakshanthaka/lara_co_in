package com.lara.c2c.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.model.NewBatch;
import com.lara.c2c.repository.NewBatchRepository;

@Service
public class NewBatchService {
	@Autowired
	private NewBatchRepository newBatchRepo;
	
	public List<NewBatch> _getAllNewBatches()
	{
		List<NewBatch> newBatchRecords = new ArrayList<NewBatch>();
		newBatchRepo.findAll().forEach(newBatchRecords :: add);
		return newBatchRecords;
	}
	
	
	
}
