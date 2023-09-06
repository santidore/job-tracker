
package com.santidore.jobtracker.dto;

import java.io.Serializable;


public class JobApplicationCreateDTO implements Serializable {
    private String position;
    private String companyName;
    private String recruiter;

    private static final int MAX_POSITION_LENGTH = 50;
    private static final int MAX_RECRUITER_LENGTH = 100;
    private static final int MAX_COMPANY_LENGTH = 100;
    
// Getters y setters

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

