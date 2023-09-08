
package com.santidore.jobtracker.controllers;

import com.santidore.jobtracker.dto.JobApplicationCreateDTO;
import com.santidore.jobtracker.dto.JobApplicationUpdateDTO;
import com.santidore.jobtracker.dto.JobApplicationUpdateStatusDTO;
import com.santidore.jobtracker.entities.JobApplication;
import com.santidore.jobtracker.entities.JobApplication.ApplicationStatus;
import com.santidore.jobtracker.exceptions.MyException;
import com.santidore.jobtracker.services.JobApplicationService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Santi
 */
@RestController
@RequestMapping("/jobapplication")
public class JobApplicationController {
    
    @Autowired
    private JobApplicationService jobApplicationService;
    
    @GetMapping("/list")
    public List<JobApplication> getAllApplications(){
        return jobApplicationService.listAllApplications();
    }
    
    @PostMapping("/listbycompany/")
    public ResponseEntity<List<JobApplication>> getAllApplicationsByCompany(@RequestBody Map<String, String> requestBody) throws MyException {
        String companyName = requestBody.get("companyName");
        try {
            List<JobApplication> applications = jobApplicationService.findByCompany(companyName);
            return ResponseEntity.ok(applications);
        } catch (MyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    @PutMapping("/changestatus/{id}")
    public ResponseEntity<String> changeStatus(@PathVariable String id, @RequestBody JobApplicationUpdateStatusDTO updateStatusDTO) {
        if (updateStatusDTO.isValid()) {
            try {
                jobApplicationService.updateApplicationStatus(updateStatusDTO);
                return ResponseEntity.ok("Status updated successfully.");
            } catch (MyException e) {
                return ResponseEntity.badRequest().body("Error updating status: " + e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid status update request. Please provide the application ID and the new status in JSON format. E.g: \n"
                    + "{\n"
                    + "    \"id\": \"131ead8d-0e52-48dc-bb9f-4144769da67f\",\n"
                    + "    \"newStatus\": \"(first_interview, second_interview, technical_test, rejected, hired)\",\n"
                    + "}");
        }
    }
    
    @PostMapping("/create")
    public ResponseEntity<String> createApplication(@RequestBody JobApplicationCreateDTO createDTO) {
        if (createDTO.isValid()) {
            try {
                jobApplicationService.createJobApplication(
                        createDTO.getPosition(),
                        createDTO.getCompanyName(),
                        createDTO.getRecruiter()
                );
                return ResponseEntity.status(HttpStatus.CREATED).body("Job application created successfully.");
            } catch (MyException e) {
                return ResponseEntity.badRequest().body("Error creating job application: " + e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid job application data. Please make sure to provide a request in JSON format. E.g:\n"
                    + "{\n"
                    + "    \"position\": \"Software Developer\",\n"
                    + "    \"companyName\": \"My Company\",\n"
                    + "    \"recruiter\": \"Jhon Recruiter\"\n"
                    + "}");
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable String id){
        // Checks if the ID is provided
        if (id == null || id.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid Job Application ID.");
        }
        try {
            jobApplicationService.deleteJobApplication(id);
            return ResponseEntity.status(HttpStatus.CREATED).body("Job Application ID: \" " + id + "\" deleted succesfuly.");
        }catch (MyException e){
            return ResponseEntity.badRequest().body("Error deleting the Job Application.");
        }
    }
    
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> editApplication(@PathVariable String id, @RequestBody Map<String, Object> updates) throws ParseException {
        try {
            // Checks if the ID is provided
            if (id == null || id.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid job application ID.");
            }

            // Calls service to update if there are valid updates
            if (updates != null && !updates.isEmpty()) {
                JobApplicationUpdateDTO updateDTO = new JobApplicationUpdateDTO();
                updateDTO.setId(id);

                // Apply updates to the DTO
                if (updates.containsKey("position")) {
                    updateDTO.setPosition(updates.get("position").toString());
                }
                if (updates.containsKey("companyName")) {
                    updateDTO.setCompanyName(updates.get("companyName").toString());
                }
                if (updates.containsKey("recruiter")) {
                    updateDTO.setRecruiter(updates.get("recruiter").toString());
                }
                if (updates.containsKey("newStatus")) {
                    try {
                        ApplicationStatus newStatus = ApplicationStatus.valueOf(updates.get("newStatus").toString());
                        updateDTO.setNewStatus(newStatus);
                    } catch (IllegalArgumentException e) {
                        return ResponseEntity.badRequest().body("Invalid job status provided: " + updates.get("newStatus"));
                    }
                }
                if (updates.containsKey("applicationDate")) {
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date parsedDate = dateFormat.parse(updates.get("applicationDate").toString());
                        updateDTO.setApplicationDate(parsedDate);
                    } catch (ParseException e) {
                        return ResponseEntity.badRequest().body("Invalid date format for applicationDate.");
                    }
                }

                // Call the service to update
                jobApplicationService.updateJobApplication(updateDTO);

                return ResponseEntity.status(HttpStatus.CREATED).body("Job application updated successfully.");
            } else {
                // No valid updates provided, return an informative message
                return ResponseEntity.badRequest().body("No valid updates provided. Please include at least one valid update field in the request.");
            }
        } catch (MyException e) {
            return ResponseEntity.badRequest().body("Error updating job application: " + e.getMessage());
        }
    }
}
