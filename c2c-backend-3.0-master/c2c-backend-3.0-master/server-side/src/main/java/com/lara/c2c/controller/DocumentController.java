package com.lara.c2c.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lara.c2c.service.DocumentService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/video")
public class DocumentController {
	
	@Autowired
	private DocumentService documentService;
	
	@PostMapping("/uploadDocumentUrl") 
    public ResponseEntity<Void> singleFileUpload(@RequestParam("file") MultipartFile file) {
		String UPLOADED_FOLDER = "D:\\Ritesh\\practice\\springboot\\src\\main\\resources\\temp\\";
        if (file.isEmpty()) {
           System.out.println("Empty file");
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);  
            documentService.insertDocumentRecords(path.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(null);
    }
}
