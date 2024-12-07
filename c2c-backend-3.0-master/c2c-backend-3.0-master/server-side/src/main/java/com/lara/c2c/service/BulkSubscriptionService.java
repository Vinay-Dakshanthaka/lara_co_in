package com.lara.c2c.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.dto.subscription.ManageSubscriptionResponse;
import com.lara.c2c.model.BulkSubscriptionCoursePackages;
import com.lara.c2c.model.ExceptionReport;
import com.lara.c2c.model.LearnerCourse;
import com.lara.c2c.repository.LearnerCourseRepository;

@Service
public class BulkSubscriptionService {

	@Autowired
	private LearnerService learnerService;
	
	@Autowired
	private LearnerCourseRepository learnerCourseRepo;
	
	public void saveSubscriptions(String filePath) throws IOException {
	      
	 	FileInputStream excelFile = new FileInputStream(new File(filePath));
	    @SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
	    XSSFSheet worksheet = workbook.getSheetAt(0);
	    BulkSubscriptionCoursePackages bulkSubscriptionCoursePackages = new BulkSubscriptionCoursePackages();
	    
	    for(int i=1;i<worksheet.getPhysicalNumberOfRows(); i++) {	        
	    	XSSFRow row = worksheet.getRow(i);
	    	bulkSubscriptionCoursePackages.setEmail(row.getCell(0).getStringCellValue());
	    	bulkSubscriptionCoursePackages.setActivatedCoursePackageIds(row.getCell(1).getStringCellValue());
	    	bulkSubscriptionCoursePackages.setDeactivatedCoursePackageIds(row.getCell(2).getStringCellValue());
	    	//System.out.println(i + ":" + bulkSubscriptionCoursePackages);
	    	saveOneUserSubscription(bulkSubscriptionCoursePackages);
	    }
	}
	
	private ManageSubscriptionResponse saveOneUserSubscription(BulkSubscriptionCoursePackages bulkSubscriptionCoursePackages) {
		ManageSubscriptionResponse response = new ManageSubscriptionResponse();			
		try {
			//activation
			String userId = learnerService.findUserId(bulkSubscriptionCoursePackages.getEmail());
			//System.out.println("userId:" + userId);
			//System.out.println("activatedPackages:" + bulkSubscriptionCoursePackages.getActivatedCoursePackageIds());
			//System.out.println("deactivatedPackages:" + bulkSubscriptionCoursePackages.getDeactivatedCoursePackageIds());
			//System.out.println("!bulkSubscriptionCoursePackages.getActivatedCoursePackageIds().equals(\"na\"):" + !bulkSubscriptionCoursePackages.getActivatedCoursePackageIds().equals("na"));
			if(!bulkSubscriptionCoursePackages.getActivatedCoursePackageIds().equals("na")) {
				//System.out.println("inside if");
				String[] activatedPackages = bulkSubscriptionCoursePackages.getActivatedCoursePackageIds().split(",");
				List<Integer> activatedPackagesList = new ArrayList<Integer>();
				for(String str : activatedPackages) {
					if(str.trim().length() != 0) {
						activatedPackagesList.add(Integer.parseInt(str.trim()));
					}
				}
				//System.out.println("userId:" + userId + "activatedPackages:" + activatedPackagesList);
				for(int i = 0; i < activatedPackagesList.size(); i++) {
					Optional<LearnerCourse> lrCourse = learnerCourseRepo.findByUserIdAndCoursePackageId(userId, activatedPackagesList.get(i));
					if(lrCourse.isPresent()) {
						learnerCourseRepo.activateLearnerCoursePackage(userId, activatedPackagesList.get(i));
					}else {
						LearnerCourse lrCourseObj = new LearnerCourse();
						lrCourseObj.setUserId(userId);
						lrCourseObj.setCoursePackageId(activatedPackagesList.get(i));
						learnerCourseRepo.save(lrCourseObj);
					}
				}
			}
			//deActivation
			if(bulkSubscriptionCoursePackages.getDeactivatedCoursePackageIds() != null && 
					bulkSubscriptionCoursePackages.getDeactivatedCoursePackageIds().length() != 0 &&
					!bulkSubscriptionCoursePackages.getDeactivatedCoursePackageIds().equals("na")) {
				String[] deactivatedPackages = bulkSubscriptionCoursePackages.getDeactivatedCoursePackageIds().split(",");
				List<Integer> deactivatedPackagesList = new ArrayList<Integer>();
				for(String str : deactivatedPackages) {
					if(str.trim().length() != 0) {
						deactivatedPackagesList.add(Integer.parseInt(str.trim()));
					}
				}			
				for(int i=0; i<deactivatedPackagesList.size(); i++) {
					Optional<LearnerCourse> lrCourse = learnerCourseRepo.findByUserIdAndCoursePackageId(userId, deactivatedPackagesList.get(i));
					if(lrCourse.isPresent()) {
						learnerCourseRepo.deActivateLearnerCoursePackage(userId, deactivatedPackagesList.get(i));
					}
				}
			}
		}catch(Exception ex) {
			response.setExceptionReport(new ExceptionReport(ex));
		}
		return response;	
	}
	
	
	
	
	
	
}
