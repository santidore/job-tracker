
package com.santidore.jobtracker.repositories;

import com.santidore.jobtracker.entities.Company;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Santi
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, String>{
    
    @Query("SELECT c FROM Company c WHERE c.companyName = :companyName")
    public Optional<Company> findByName(@Param("companyName") String companyName);

}
