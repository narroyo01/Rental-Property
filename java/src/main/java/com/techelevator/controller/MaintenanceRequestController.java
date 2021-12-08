package com.techelevator.controller;

import com.techelevator.dao.JdbcMaintenanceRequestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class MaintenanceRequestController {

    @Autowired
    JdbcMaintenanceRequestDao jdbcMaintenanceRequestDao;

    @PreAuthorize("hasAnyRole('ROLE_MAINTENANCE', 'ROLE_ADMIN', 'ROLE_LANDLORD')")
    @GetMapping("/maintenance-request/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        return new ResponseEntity<>(jdbcMaintenanceRequestDao.getByAssignedTechnicianId(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_MAINTENANCE', 'ROLE_ADMIN', 'ROLE_LANDLORD')")
    @PostMapping("/maintenance-request/complete/{id}")
    public ResponseEntity<?> setIsComplete(@PathVariable int id) {
        jdbcMaintenanceRequestDao.markAsComplete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
