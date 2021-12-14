package com.techelevator.dao;

import com.techelevator.model.MaintenanceRequest;
import com.techelevator.model.MaintenanceType;

import java.util.List;

public interface MaintenanceRequestDao {
    void markAsComplete(int id);

    List<MaintenanceRequest> getByAssignedTechnicianId(int id);

    int maintenanceRequest(MaintenanceRequest maintenanceRequest );

    List<MaintenanceType> getTypes();

    void assignTechnician(int maintenanceRequestId, int technicianId);

    List <MaintenanceRequest> getOpenMaintenanceRequests();

}
