package com.lara.c2c.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.c2c.model.DocumentTutorial;
import com.lara.c2c.repository.DocumentTutorialRepository;

@Service
public class DocumentService {

	@Autowired
	private DocumentTutorialRepository documentRepository;
	
	public void insertDocumentRecords(String filePath) throws IOException {
		FileInputStream excelFile = new FileInputStream(new File(filePath));
	    @SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
	    XSSFSheet worksheet = workbook.getSheetAt(0);

	    for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
	      
	    	XSSFRow row = worksheet.getRow(i);	    		    
	    	DocumentTutorial documentTutorial = new DocumentTutorial();
	    		    
	    	documentTutorial.setMicroTopicId(row.getCell(0).getStringCellValue());
	    	documentTutorial.setDocumentUrl(row.getCell(1).getStringCellValue());
						
	    	saveDocumentTutorials(documentTutorial);		
	    }
	}
	
	public void saveDocumentTutorials(DocumentTutorial documentTutorial) {
		documentRepository.save(documentTutorial);
	}
}
