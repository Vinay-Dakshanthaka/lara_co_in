package com.lara.c2c.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lara.c2c.service.BulkSubscriptionService;
import com.lara.c2c.utility.Constants;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/bulksubscription")
public class BulkSubscriptionController {
	
	@Autowired
	private BulkSubscriptionService bulkSubscriptionService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/bulkupload") 
    public ResponseEntity<Void> singleFileUpload(@RequestParam("file") MultipartFile file) {
		
        if (file.isEmpty()) {
           System.out.println("Empty file");
        }
        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(Constants.TEMP_FILE_UPLOAD_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);  
            bulkSubscriptionService.saveSubscriptions(path.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(null);
    }

	
	
}
