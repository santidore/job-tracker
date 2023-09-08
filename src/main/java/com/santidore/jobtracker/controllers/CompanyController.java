
package com.santidore.jobtracker.controllers;

import com.santidore.jobtracker.dto.CompanyCreateDTO;
import com.santidore.jobtracker.entities.Company;
import com.santidore.jobtracker.exceptions.MyException;
import com.santidore.jobtracker.services.CompanyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Santi
 */
@RestController
@RequestMapping("/company")
public class CompanyController {
    
    @Autowired
    private CompanyService companyService;
    
    @GetMapping("/list")
    public List<Company> getAllCompanies(){
        return companyService.listAllCompanies();
    }
    
    @PostMapping("/create")
    public ResponseEntity<String> createCompany(@RequestBody CompanyCreateDTO createDTO){
        if (createDTO.isValid()) {
            try {
                companyService.createCompany(createDTO.getCompanyName());
                return ResponseEntity.status(HttpStatus.CREATED).body("Company added to the database succesfully.");
            } catch (MyException e) {
                return ResponseEntity.badRequest().body("Error adding the new company: " + e.getMessage());
            }
        } else {
           return ResponseEntity.badRequest().body("Invalid company data. Please make sure to provide a request in JSON format. E.g:\n"
                    + "{\n"
                    + "    \"companyName\": \"My Company\",\n"
                    + "}");
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable String id){
                // Checks if the ID is provided
        if (id == null || id.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid Company ID.");
        }
        try {
            companyService.deleteCompany(id);
            return ResponseEntity.status(HttpStatus.CREATED).body("Company ID: \" " + id + "\" deleted succesfuly.");
        }catch (MyException e){
            return ResponseEntity.badRequest().body("Error deleting the Company.");
        }
    }
}
