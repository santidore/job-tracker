
package com.santidore.jobtracker.services;

import com.santidore.jobtracker.entities.Company;
import com.santidore.jobtracker.entities.JobApplication;
import com.santidore.jobtracker.exceptions.MyException;
import com.santidore.jobtracker.repositories.CompanyRepository;
import com.santidore.jobtracker.repositories.JobApplicationRepository;
import java.util.Comparator;
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
public class CompanyService {
    
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    JobApplicationRepository jobApplicationRepository;
    
    //Creates a new company if it's not been previously created
    @Transactional
    public Company createCompany (String companyName) throws MyException {
        Optional<Company> existingCompany = companyRepository.findByName(companyName);

        if (existingCompany.isPresent()) {
            throw new MyException("La compañía con el nombre '" + companyName + "' ya existe.");
        } else {
            Company c = new Company();
            c.setName(companyName);
            return companyRepository.save(c);
        }
    }
    
    //Checks if the company provided by the user already exists in the database, or else creates it.
    public Company validateCompany(String companyName){
        
        Company company;
        Optional<Company> optionalCompany = companyRepository.findByName(companyName);
        
        if (optionalCompany.isPresent()){
            
            return company = optionalCompany.get();
            
        }else {
            
            return company = createCompany(companyName);
            
        }
    }
    
    //Returns a sorted list of companies
    public List<Company> listAllCompanies() {
        List<Company> companyList = companyRepository.findAll();
        companyList.sort(Comparator.comparing(Company::getName));
        return companyList;
    }
    
    @Transactional
    public void deleteCompany(String id) throws MyException{
        
        if (companyRepository.existsById(id)){
            List<JobApplication> jobApplicationsbycompany = jobApplicationRepository.findByCompanyId(id);
            jobApplicationRepository.deleteAll(jobApplicationsbycompany);
            companyRepository.deleteById(id);
        } else {
            throw new MyException("The company ID: " + id + " was not found.");
        }
    }
    
}
