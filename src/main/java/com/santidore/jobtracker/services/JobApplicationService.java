
package com.santidore.jobtracker.services;

import com.santidore.jobtracker.dto.JobApplicationUpdateDTO;
import com.santidore.jobtracker.dto.JobApplicationUpdateStatusDTO;
import com.santidore.jobtracker.entities.Company;
import com.santidore.jobtracker.entities.JobApplication;
import com.santidore.jobtracker.entities.JobApplication.ApplicationStatus;
import com.santidore.jobtracker.exceptions.MyException;
import com.santidore.jobtracker.repositories.CompanyRepository;
import com.santidore.jobtracker.repositories.JobApplicationRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Santi
 */
@Service
public class JobApplicationService {
    
    @Autowired
    JobApplicationRepository jobApplicationRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    private CompanyService companyService;
    
    private static final int MAX_POSITION_LENGTH = 50;
    
    @Transactional
    public void createJobApplication(String position, String companyName, String recruiter) throws MyException {
        
        Company company = companyService.validateCompany(companyName);
        //Creating new Job Application
        JobApplication ja = new JobApplication();
        
        ja.setPosition(position);
        ja.setApplicationDate(new Date());
        ja.setCompany(company);
        ja.setRecruiter(recruiter);
        ja.setStatus(ApplicationStatus.applied);
        jobApplicationRepository.save(ja);
    }
    
    public void deleteJobApplication(String id) throws MyException {
        
        if (jobApplicationRepository.existsById(id)) {
            jobApplicationRepository.deleteById(id);
    } else {
            throw new MyException("Job Application not found for deletion");
        }
    }
    
    @Transactional
    public void updateJobApplication(JobApplicationUpdateDTO updateDTO) throws MyException {
        //First we check if there is an existing application under the ID provided
        Optional<JobApplication> existingApplication = jobApplicationRepository.findById(updateDTO.getId());

        if (!existingApplication.isPresent()) {
            throw new MyException("Job application not found with ID: " + updateDTO.getId());
        }

        //We get the existing application
        JobApplication jobApplication = existingApplication.get();

        //Applies updates provided by the DTO
        if (updateDTO.getPosition() != null) {
            jobApplication.setPosition(updateDTO.getPosition());
        }

        if (updateDTO.getCompanyName() != null) {
            String companyName = updateDTO.getCompanyName();
            Company company = companyRepository.findByName(companyName).orElseGet(() -> {
                // Si la empresa no existe, créala
                Company newCompany = new Company();
                newCompany.setName(companyName);
                return companyRepository.save(newCompany);
            });
            jobApplication.setCompany(company);
        }

        if (updateDTO.getRecruiter() != null) {
            jobApplication.setRecruiter(updateDTO.getRecruiter());
        }

        if (updateDTO.getApplicationDate() != null) {
            jobApplication.setApplicationDate(updateDTO.getApplicationDate());
        }

        jobApplicationRepository.save(jobApplication);
    }

    @Transactional
    public void updateApplicationStatus(JobApplicationUpdateStatusDTO updateStatusDTO) throws MyException {
        String id = updateStatusDTO.getId();
        ApplicationStatus newStatus = updateStatusDTO.getNewStatus();

        Optional<JobApplication> optionalApplication = jobApplicationRepository.findById(id);

        if (optionalApplication.isPresent()) {
            JobApplication application = optionalApplication.get();
            application.setStatus(newStatus);
            jobApplicationRepository.save(application);
        } else {
            throw new MyException("Error. Application not found ID " + id);
        }
    }


    public JobApplication getOne(String id){
        
        return jobApplicationRepository.getOne(id);
    } 
    
    public List<JobApplication> findByPosition(String position) throws MyException {
        
        if (position == null || position.isEmpty()) {
            throw new MyException("Position cannot be empty");
        }
        if (position.length() > MAX_POSITION_LENGTH) {
            throw new MyException("Position length cannot exceed " + MAX_POSITION_LENGTH + " characters");
        }
        
        List<JobApplication> applicationsByPosition = jobApplicationRepository.findByPosition(position);
        
        if (applicationsByPosition.isEmpty()){
            throw new MyException("Sorry, we couldn't find any job application :(");
        }
        
        return applicationsByPosition;
    }
    
    public List<JobApplication> findByCompany(String companyName) throws MyException {
        
        if (companyName == null || companyName.isEmpty()) {
            throw new MyException("Company cannot be empty");
        }
        
        List<JobApplication> applicationsByCompany;
        applicationsByCompany = jobApplicationRepository.findByCompanyName(companyName);
        
        if (applicationsByCompany.isEmpty()){
            throw new MyException("Sorry, we couldn't find any job applications applied for " + companyName);
        }
        
        return applicationsByCompany;
    }
    
    public List<JobApplication> listAllApplications() {

        return jobApplicationRepository.findAll();

    }
    
}
