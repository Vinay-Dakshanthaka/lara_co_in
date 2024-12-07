package com.lara.c2c.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lara.c2c.model.PackageData;
import com.lara.c2c.model.PlacementData;
import com.lara.c2c.model.UsersForMarketing;
import com.lara.c2c.repository.PackageDataRepository;
import com.lara.c2c.repository.PlacementDataRepository;
import com.lara.c2c.repository.UsersForMarketingRepository;

import lombok.var;


@Service
public class AdminService {

	@Autowired
	private UsersForMarketingRepository usersForMarketingRepository; 
	
	@Autowired
	private PackageDataRepository packageDataRepository;
	
	@Autowired
	private PlacementDataRepository placementDataRepository;
	
	public void saveEmails(String filePath) throws IOException {
	      
	 	FileInputStream excelFile = new FileInputStream(new File(filePath));
	    @SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
	    XSSFSheet worksheet = workbook.getSheetAt(0);
	    UsersForMarketing user = null;
	    
	    for(int i=1;i<worksheet.getPhysicalNumberOfRows(); i++) {	   
	    	user = new UsersForMarketing();
	    	XSSFRow row = worksheet.getRow(i);
	    	user.setEmail(row.getCell(3).getStringCellValue());
	    	user.setFirstName(row.getCell(1).getStringCellValue());
	    	user.setMobileNumber(Double.toString(row.getCell(4).getNumericCellValue()));
	    	user.setSubscribeStatus(1);
	    	usersForMarketingRepository.save(user);
	    }
	}	
	
	
	//package controller area
	
	public PackageData save(PackageData packageData)
	{
		return packageDataRepository.save(packageData);
	}
	
	public Iterable<PackageData> get()
	{
		return packageDataRepository.findAll();
	}
	
	public PackageData update(PackageData packageData)
	{
		return packageDataRepository.save(packageData);
	}
	
	public Iterable<PackageData> getSelected()
	{
		return packageDataRepository.getselected();
	}
	

	//placement data controller area
	
	public PlacementData save(PlacementData placementData)
	{
		return placementDataRepository.save(placementData);
	}
	
	public Iterable<PlacementData> getPlacement()     
	{
		return placementDataRepository.findAll();
	}
	
	public PlacementData update(PlacementData placementData)
	{
		return placementDataRepository.save(placementData);
	}
	
	public Iterable<PlacementData> saveSelected(Iterable<PlacementData> placementData)
	{
		return placementDataRepository.saveAll(placementData);
	}
	
	public Iterable<PlacementData> getSelectedPlacement()
	{
		return placementDataRepository.getselectedPlacement();
	}
	
}
