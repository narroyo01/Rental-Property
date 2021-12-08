package com.techelevator.dao;

import com.techelevator.model.Property;

import java.util.List;

public interface PropertyDao {

    int addProperty(Property property);

    void updateProperty(int propertyId, Property property);

}
