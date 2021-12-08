package com.techelevator.dao;

import com.techelevator.model.MaintenanceRequest;

import java.util.List;

public interface MaintenanceRequestDao {
    void markAsComplete(int id);
    List<MaintenanceRequest> getByAssignedTechnicianId(int id);
}
