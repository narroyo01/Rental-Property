package com.techelevator.model;

public class MaintenanceType {
    int maintenanceTypeId;
    String description;

    public MaintenanceType(int maintenanceTypeId, String description) {
        this.maintenanceTypeId = maintenanceTypeId;
        this.description = description;
    }

    public MaintenanceType() {
    }

    public int getMaintenanceTypeId() {
        return maintenanceTypeId;
    }

    public void setMaintenanceTypeId(int maintenanceTypeId) {
        this.maintenanceTypeId = maintenanceTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
