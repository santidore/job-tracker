
package com.santidore.jobtracker.repositories;

import com.santidore.jobtracker.entities.JobApplication;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Santi
 */
@Repository
public interface JobApplicationRepository extends JpaRepository <JobApplication, String> {
    
    @Query("SELECT j FROM JobApplication j WHERE j.position = :position")
    public List<JobApplication> findByPosition(@Param ("position") String position);
    
    @Query("SELECT j FROM JobApplication j WHERE j.applicationDate = :applicationDate")
    public List<JobApplication> findByDate(@Param ("applicationDate") Date applicationDate);
    
    @Query("SELECT j FROM JobApplication j JOIN j.company c WHERE c.companyName = :companyName")
    public List<JobApplication> findByCompanyName(@Param("companyName") String companyName);
    
    @Query("SELECT j FROM JobApplication j JOIN j.company c WHERE c.id = :id")
    public List<JobApplication> findByCompanyId(@Param("id") String id);

}

