
package com.santidore.jobtracker.dto;

import java.io.Serializable;

/**
 *
 * @author Santi
 */
public class CompanyCreateDTO implements Serializable {

    String companyName;

    private static final int MAX_COMPANY_LENGTH = 100;
    
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public boolean isValid(){
        return isValidCompanyName(companyName);
    }
    
    public boolean isValidCompanyName(String companyName) {

        return companyName != null && !companyName.isEmpty() && companyName.length() <= MAX_COMPANY_LENGTH;
    }
    
}
