package com.lara.c2c.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.lara.c2c.dto.data.SaveSuccessStatus;
import com.lara.c2c.dto.data.UpdateSuccessStatus;
import com.lara.c2c.model.PackageData;
import com.lara.c2c.model.PlacementData;
import com.lara.c2c.model.Testimonial;
import com.lara.c2c.repository.PackageDataRepository;
import com.lara.c2c.repository.PlacementDataRepository;
import com.lara.c2c.service.AdminService;
import com.lara.c2c.service.BulkSubscriptionService;
import com.lara.c2c.service.TestimonialService;
import com.lara.c2c.utility.Constants;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")

public class AdminController {
	
	@Autowired
	private TestimonialService testimonialService;
	
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private PackageDataRepository packageDataRepository;
	
	@Autowired
	private PlacementDataRepository placementDataRepository;
	
	
	@PostMapping("/addTestimonial")
	public ResponseEntity<Boolean> addTestimonialData(@RequestBody Testimonial testimonialRequest){		
		boolean result = testimonialService._addTestimonial(testimonialRequest);
		return ResponseEntity.ok().body(result);		
	}
	
	@GetMapping("/findTestimonialById/{testimonialId}")
	public ResponseEntity<Testimonial> getTestimonialById(@PathVariable int testimonialId){
		Testimonial testimonialRecord = testimonialService._getTestimonialById(testimonialId);		
		return ResponseEntity.ok().body(testimonialRecord);
		
	}
	
	@DeleteMapping("/deleteTestimonialById/{testimonialId}")
	public ResponseEntity<Boolean> deleteTestimonialById(@PathVariable int testimonialId){
		Boolean result = testimonialService._deleteTestimonial(testimonialId);
		return ResponseEntity.ok().body(result);
	}
	
		
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/emailIdsForBulkMessages") 
    public ResponseEntity<Void> singleFileUpload(@RequestParam("file") MultipartFile file) {
		
        if (file.isEmpty()) {
           System.out.println("Empty file");
        }
        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(Constants.TEMP_FILE_UPLOAD_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);  
            adminService.saveEmails(path.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(null);
    }
	
	@PostMapping("package")
	public ResponseEntity<SaveSuccessStatus> save(@RequestBody PackageData packageData)
	{
		adminService.save(packageData);
		
		SaveSuccessStatus status = new SaveSuccessStatus();
		status.setMessage("Data Saved Successfully");
		status.setStatus(true);
		return ResponseEntity.ok(status);
	}
	
	@GetMapping("selectPackage")
	public Iterable<PackageData> get()
	{
		return adminService.get();
	}
	
	 @PutMapping("updatePackage")
	    public ResponseEntity<UpdateSuccessStatus> updatePlacementData(@RequestBody PackageData packageData) {
         
		 PackageData data = packageDataRepository.findById(packageData.getId()).get();
		 
		 data.setPackageName(packageData.getPackageName());
		 data.setStartDate(packageData.getStartDate());
		 data.setDuration(packageData.getDuration());
		 data.setDemoStartDate(packageData.getDemoStartDate());
		 data.setDemoEndDate(packageData.getDemoEndDate());
		 data.setScholarshipDate(packageData.getScholarshipDate());
		 data.setSelected(packageData.getSelected());
		 packageDataRepository.save(data);
	
         
         UpdateSuccessStatus status = new UpdateSuccessStatus();
         status.setMessage("Data Updated Successfully");
         status.setStatus(true);
        return ResponseEntity.ok(status);
     }
	 
	 @GetMapping("selectedPackage")
	  public Iterable<PackageData> getSlectedPlacement()
	  {
		  return adminService.getSelected();
	  }
	 
		

	    @PostMapping("placement")
	    public ResponseEntity<SaveSuccessStatus> save(@RequestBody PlacementData placementData) {
	    	
	    	if(placementData.getClass() != null)
	    	{
	        adminService.save(placementData);
	        System.out.println("done");

	        SaveSuccessStatus status = new SaveSuccessStatus();
	        status.setMessage("Data Saved Successfull  y");
	        status.setStatus(true);
	        return ResponseEntity.ok(status);
	    	}
	    	else
	    	{
	    		SaveSuccessStatus status = new SaveSuccessStatus();
	    		status.setMessage("Please Don't provide null values");
	    		return ResponseEntity.ok(status);
	    	}
	    }
		
		@GetMapping("selectPlacement")
		public Iterable<PlacementData> getPlacement()
		{
			return adminService.getPlacement();
		}
		
		
		 @PutMapping("updatePlacement")
		    public ResponseEntity<UpdateSuccessStatus> updatePlacementData(@RequestBody PlacementData placementData) {
	         
			 if(placementData.getClass() != null)
			 {
			 PlacementData data = placementDataRepository.findById(placementData.getId()).get();
			 
	         data.setCompanyName(placementData.getCompanyName());
	         data.setDate(placementData.getDate());
	         data.setStudentAttended(placementData.getStudentAttended());
	         data.setStudentPlaced(placementData.getStudentPlaced());
	         data.setSalaryPackage(placementData.getSalaryPackage());
	         data.setSelected(placementData.getSelected());
		     placementDataRepository.save(placementData);
			 
		     UpdateSuccessStatus status = new UpdateSuccessStatus();
		     status.setMessage("Data Updated Successfully");
		     status.setStatus(true);
		     return ResponseEntity.ok(status);
			 }
			 else
			 {
			 UpdateSuccessStatus status = new UpdateSuccessStatus();
			 status.setMessage("Please don't provide null values");
			 return ResponseEntity.ok(status);
			 }
	 }
		 
		  @GetMapping("selectedPlacement")
		  public Iterable<PlacementData> getSlected()
		  {
			  return adminService.getSelectedPlacement();
		  }			    
		  
}
