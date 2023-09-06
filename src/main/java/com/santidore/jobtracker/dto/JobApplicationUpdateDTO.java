
package com.santidore.jobtracker.dto;

import com.santidore.jobtracker.entities.JobApplication.ApplicationStatus;
import java.io.Serializable;

import java.util.Date;

public class JobApplicationUpdateDTO implements Serializable {
    private String id;
    private String position;
    private String companyName;
    private String recruiter;
    private Date applicationDate;
    private ApplicationStatus newStatus;

    private static final int MAX_POSITION_LENGTH = 50;
    private static final int MAX_RECRUITER_LENGTH = 100;
    private static final int MAX_COMPANY_LENGTH = 100;
    
    //Constructor, getters & setters

    public JobApplicationUpdateDTO() {
    }

    public JobApplicationUpdateDTO(String id, String position, String companyName, String recruiter, Date applicationDate, ApplicationStatus newStatus) {
        this.id = id;
        this.position = position;
        this.companyName = companyName;
        this.recruiter = recruiter;
        this.applicationDate = applicationDate;
        this.newStatus = newStatus;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(String recruiter) {
        this.recruiter = recruiter;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public ApplicationStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(ApplicationStatus newStatus) {
        this.newStatus = newStatus;
    }
    
    
   
    // Validating methods
    public boolean isValid() {
        return isValidPosition(position) && isValidCompanyName(companyName) && isValidRecruiter(recruiter);
    }

    private boolean isValidPosition(String position) {

        return position != null && !position.isEmpty() && position.length() <= MAX_POSITION_LENGTH;
    }

    private boolean isValidCompanyName(String companyName) {

        return companyName != null && !companyName.isEmpty() && companyName.length() <= MAX_COMPANY_LENGTH;
    }

    private boolean isValidRecruiter(String recruiter) {

        return recruiter != null && !recruiter.isEmpty() && recruiter.length() <= MAX_RECRUITER_LENGTH;
    }
}

