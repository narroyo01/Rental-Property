package com.techelevator.model;

public class Property {

    private int propertyId;
    private String address;
    private Integer rent;
    private Boolean isAvailable;
    private Integer tenantId;
    private String username;
    private String imageUrl;
    private String description;

    public Property(int propertyId, String address, Integer rent, Boolean isAvailable, Integer tenantId, String imageUrl, String username, String description) {
        this.propertyId = propertyId;
        this.address = address;
        this.rent = rent;
        this.isAvailable = isAvailable;
        this.tenantId = tenantId;
        this.imageUrl = imageUrl;
        this.username = username;
        this.description = description;
    }

    public Property() {
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public Boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public void setRent(Integer rent) {
        this.rent = rent;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
