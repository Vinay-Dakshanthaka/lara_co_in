package com.lara.c2c.service;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lara.c2c.dto.data.ClassRecordsDto;
import com.lara.c2c.dto.data.ClassUpdateDto;
import com.lara.c2c.dto.data.SaveSuccessStatus;
import com.lara.c2c.dto.data.SuccessStatus;
import com.lara.c2c.model.ClassRecord;
import com.lara.c2c.repository.ClassRecordRepository;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


@Service
public class ClassRecordService {
    
	@Autowired
	private ClassRecordRepository classRecordRepository;
	
	
	public SaveSuccessStatus saveAll(ClassRecordsDto classList){
		SaveSuccessStatus status = SaveSuccessStatus.builder()
        		    .message("Please enter input fields")
        		    .status(false)
        		    .build();
         
         if(classList.getClassList() == null)return status;
        	 
         List<ClassRecord> list = new ArrayList<>();
         
         classList.getClassList().forEach(
        		 record->{
        			record.setId(record.getId());
        			System.out.println(record.getId());
        			record.setDate(classList.getCurrentDate());
        			record.setStartTime(record.getStartTime());
        			record.setEndTime(record.getEndTime());        			
        			record.setBatchName(record.getBatchName());
        			record.setInstructor(record.getInstructor());
        			record.setClassType(record.getClassType());
        			record.setTopic(record.getTopic());
        			list.add(record);
        		 });
   
         classRecordRepository.saveAll(list);
		 status.setMessage("saved all records");
		 status.setStatus(true);
		 return status;
	}
	
	public Iterable<ClassRecord> getSelectAll()
	{
		List<ClassRecord> selectRecords=classRecordRepository.getSelectAll();
		return selectRecords;
	}
	
	
	//update
	public SuccessStatus update(ClassUpdateDto classUpdateDto) {
		System.out.println(classUpdateDto);
		System.out.println("1");
		SuccessStatus status =SuccessStatus.builder()
	  			   .message("please provider valid details")
	  	           .status(false)
	  	           .build();
	  	
	  	if(classUpdateDto.getId()==null) {
	  		return status;
	  	}
	  	
	  	Optional<ClassRecord> fetchedRecord = classRecordRepository.findById(classUpdateDto.getId());
	  	System.out.println("2");
	  	if(!fetchedRecord.isPresent()) {
	  		status.setMessage("No such records"); 
	  		return status;
	  	}
	  		  	
// 	    fetchedRecord.get().setId(classUpdateDto.getId());
 	    fetchedRecord.get().setBatchName(classUpdateDto.getBatchName());
 	    fetchedRecord.get().setDate(classUpdateDto.getDate());
 	    fetchedRecord.get().setStartTime(classUpdateDto.getStartTime());
 	    fetchedRecord.get().setEndTime(classUpdateDto.getEndTime());
 	    fetchedRecord.get().setInstructor(classUpdateDto.getInstructor());
 	    fetchedRecord.get().setClassType(classUpdateDto.getClassType());
 	    fetchedRecord.get().setTopic(classUpdateDto.getTopic());
 	    fetchedRecord.get().setPlace(classUpdateDto.getPlace());
 	    classRecordRepository.save(fetchedRecord.get());
 	 
	    System.out.println("3");
	  	status.setMessage("updated success");
	  	status.setStatus(true);
	  	return status;
	}

	//delete the records 
	public SuccessStatus delete(Integer id) {
		System.out.println(id);
		SuccessStatus status = SuccessStatus.builder()
				    .message("No such record present")
		            .status(false)
		            .build();
		
		System.out.println("1");
		Optional<ClassRecord> fetchRecord = classRecordRepository.findById(id);
		if(!fetchRecord.isPresent()) return status;
		System.out.println("2");
		classRecordRepository.deleteById(fetchRecord.get().getId());
		status.setMessage("Success record deleted");
		status.setStatus(true);
		return status;
	}
	
	public ResponseEntity<SuccessStatus> uploadQuestions(MultipartFile file, String date){
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(file.getInputStream());
		}
		catch (Exception ex) {
			ResponseEntity.ok().body(SuccessStatus.builder().message("Something went pls upload again !").status(false).build());
		}
		
		List<ClassRecord> classList = new ArrayList<>();
		Sheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			int col = 0;
			if (row.getRowNum() == 0) {
				continue;
			}
			ClassRecord classRecord = new ClassRecord();
			try {
				classRecord.setDate(date);
				col++;
				classRecord.setStartTime(get12HrDateFormat().format(row.getCell(0).getDateCellValue()));
				col++;
				classRecord.setEndTime(get12HrDateFormat().format(row.getCell(1).getDateCellValue()));
				col++;
				classRecord.setBatchName(row.getCell(2).getStringCellValue());
				col++;
				classRecord.setInstructor(row.getCell(3).getStringCellValue());
				col++;
				classRecord.setClassType(row.getCell(4).getStringCellValue());
				col++;
				classRecord.setTopic(row.getCell(5).getStringCellValue());
				col++;
				classRecord.setPlace(row.getCell(6).getStringCellValue());
			}	
			catch(IllegalStateException ex) {
				return ResponseEntity.ok().body(
						SuccessStatus.builder()
						.message("Oops, it seems there's a type mismatch at row " + (row.getRowNum() + 1) + " and column " + col + " !")
						.status(false).build()
						);
			}
			catch(NullPointerException ex) {
				return ResponseEntity.ok().body(
						SuccessStatus.builder()
						.message("Oops, it seems there's a missing value at row " + (row.getRowNum() + 1) + " and column " + col + " !")
						.status(false).build()
						);
			}
			catch(Exception ex) {
				return ResponseEntity.ok().body(
						SuccessStatus.builder()
						.message("Oops,something went wrong, please check yout values at row " + (row.getRowNum() + 1) + " and column " + col + " !")
						.status(false).build()
						);
			}
			
			classList.add(classRecord);
		}
		
		classRecordRepository.saveAll(classList);
		
		return ResponseEntity.ok().body(SuccessStatus.builder().message("uplaoded Successfully !").status(true).build());
	}
	
	public DateTimeFormatter get12HrLocalDateFormat() {
		return DateTimeFormatter.ofPattern("hh:mm a");
	}
	
	public SimpleDateFormat get12HrDateFormat() {
		return new SimpleDateFormat("hh:mm a");
	}
	
	public DateTimeFormatter getLocalDate() {
		return DateTimeFormatter.ofPattern("dd-mm-yy");
	}
}
