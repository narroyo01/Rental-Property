package com.techelevator.controller;

import com.techelevator.dao.PropertyDao;
import com.techelevator.model.Property;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class PropertyController {

    private PropertyDao propertyDao;

    public PropertyController(PropertyDao propertyDao) {
        this.propertyDao = propertyDao;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LANDLORD')")
    @PostMapping("/property")
    public ResponseEntity<?> add(@RequestBody Property property){
        return new ResponseEntity<>(propertyDao.addProperty(property), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LANDLORD')")
    @PutMapping("/property/{id}")
    public void put(@PathVariable int id, @RequestBody Property property){
        propertyDao.updateProperty(id, property);
    }

    @GetMapping("/property/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        return new ResponseEntity<>(propertyDao.getPropertyById(id), HttpStatus.OK);
    }

    @GetMapping("/property/available")
    public ResponseEntity<?> getAvailable() {
        return new ResponseEntity<>(propertyDao.getAvailableProperties(), HttpStatus.OK);
    }

    @GetMapping("/property/")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(propertyDao.getProperties(), HttpStatus.OK);
    }

    @PostMapping("/property/{propertyId}/assign/{tenantId}")
    public ResponseEntity<?> assignTenant(@PathVariable int propertyId, @PathVariable int tenantId) {
        propertyDao.assignTenant(propertyId, tenantId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
