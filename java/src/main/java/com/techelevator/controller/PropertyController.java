package com.techelevator.controller;

import com.techelevator.dao.PropertyDao;
import com.techelevator.model.Property;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class PropertyController {

    private PropertyDao propertyDao;

    public PropertyController(PropertyDao propertyDao) {
        this.propertyDao = propertyDao;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/property")
    public ResponseEntity<?> add(@RequestBody Property property){
        return new ResponseEntity<>(propertyDao.addProperty(property), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/property/{id}")
    public void put(@PathVariable int id, @RequestBody Property property){
        propertyDao.updateProperty(id, property);
    }
}
