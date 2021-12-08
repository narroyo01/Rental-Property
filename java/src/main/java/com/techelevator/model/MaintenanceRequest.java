package com.techelevator.model;

import java.sql.Timestamp;

public class MaintenanceRequest {
    private Integer maintenanceRequestId;
    private Integer propertyId;
    private Integer typeId;
    private Integer technicianId;
    private Integer requesterId;
    private Timestamp timeStamp;
    private Boolean isComplete;
    private String email;
    private String phone;
    private String name;
    private String comments;
    private String description;
    private String address;

    public MaintenanceRequest(Integer maintenanceRequestId, Integer propertyId, Integer typeId, Integer technicianId, Integer requesterId, Timestamp timeStamp, Boolean isComplete, String email, String phone, String name, String comments, String description, String address) {
        this.maintenanceRequestId = maintenanceRequestId;
        this.propertyId = propertyId;
        this.typeId = typeId;
        this.technicianId = technicianId;
        this.requesterId = requesterId;
        this.timeStamp = timeStamp;
        this.isComplete = isComplete;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.comments = comments;
        this.description = description;
        this.address = address;
    }

    public MaintenanceRequest() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getMaintenanceRequestId() {
        return maintenanceRequestId;
    }

    public void setMaintenanceRequestId(Integer maintenanceRequestId) {
        this.maintenanceRequestId = maintenanceRequestId;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(Integer technicianId) {
        this.technicianId = technicianId;
    }

    public Integer getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(Integer requesterId) {
        this.requesterId = requesterId;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
