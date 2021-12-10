package com.techelevator.dao;

import com.techelevator.model.Property;

import java.util.List;

public interface PropertyDao {

    int addProperty(Property property);

    void updateProperty(int propertyId, Property property);

    Property getPropertyById(int id);

    List<Property> getAvailableProperties();

    List<Property> getProperties();

    void assignTenant(int propertyId, int tenantId);
}
