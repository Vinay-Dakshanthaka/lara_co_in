package com.lara.c2c.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lara.c2c.dto.data.ClassRecordsDto;
import com.lara.c2c.dto.data.ClassUpdateDto;
import com.lara.c2c.dto.data.SaveSuccessStatus;
import com.lara.c2c.dto.data.SuccessStatus;
import com.lara.c2c.model.ClassRecord;
import com.lara.c2c.service.ClassRecordService;


@RestController
@RequestMapping("class")
@CrossOrigin("*")
public class ClassRecordController {
	@Autowired
	private ClassRecordService classRecordService;
	
	@PostMapping("saveall")
	public SaveSuccessStatus saveAll(@RequestBody ClassRecordsDto classList)
	{
		return classRecordService.saveAll(classList);
	}
	
	@GetMapping("getselectall")
	public Iterable<ClassRecord> getSelectAll()
	{
		return classRecordService.getSelectAll();
	}
	
	@DeleteMapping("delete/{id}")
	public SuccessStatus delete(@PathVariable Integer id) {
		System.out.println(id);
		return classRecordService.delete(id);
	}

	@PutMapping("update")
	public SuccessStatus delete(@RequestBody ClassUpdateDto classUpdateDto)
	{
       return classRecordService.update(classUpdateDto);
	}	
	
	@PostMapping("xlsxupload")
	public ResponseEntity<SuccessStatus> uploadQuestions(@RequestParam MultipartFile file, @RequestParam String date){
		System.out.println(date);
		return classRecordService.uploadQuestions(file, date);	
//		return ResponseEntity.ok().body(SuccessStatus.builder().status(true).build());
	}
}
