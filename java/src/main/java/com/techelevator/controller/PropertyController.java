package com.techelevator.controller;

import com.techelevator.dao.PropertyDao;
import com.techelevator.model.Property;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/property")
public class PropertyController {

    private PropertyDao propertyDao;

    public PropertyController(PropertyDao propertyDao) {
        this.propertyDao = propertyDao;
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public void add(@RequestBody Property property){
        propertyDao.addProperty(property.getAddress(), property.getRent(), property.isAvailable(), property.getTenantId());
    }
}
