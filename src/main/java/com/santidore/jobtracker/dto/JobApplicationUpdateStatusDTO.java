
package com.santidore.jobtracker.dto;

import com.santidore.jobtracker.entities.JobApplication.ApplicationStatus;
import java.io.Serializable;

public class JobApplicationUpdateStatusDTO implements Serializable {
    
    private String id;
    private ApplicationStatus newStatus;
    
    public boolean isValid() {
        if (newStatus == null) {
            return false;
        }

        // Validar newStatus aquí
        for (ApplicationStatus validStatus : ApplicationStatus.values()) {
            if (validStatus == newStatus) {
                return true;
            }
        }

        return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
    public ApplicationStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(ApplicationStatus newStatus) {
        this.newStatus = newStatus;
    }
}

