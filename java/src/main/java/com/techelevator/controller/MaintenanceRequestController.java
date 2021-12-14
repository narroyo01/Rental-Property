package com.techelevator.controller;

import com.techelevator.dao.JdbcMaintenanceRequestDao;
import com.techelevator.model.MaintenanceRequest;
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

    @PostMapping("/maintenance-request/")
    public ResponseEntity<?> submitMaintenanceRequest(@RequestBody MaintenanceRequest maintenanceRequest) {
        jdbcMaintenanceRequestDao.maintenanceRequest(maintenanceRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/maintenance-types")
    public ResponseEntity<?> getTypes(){
        return new ResponseEntity<>(jdbcMaintenanceRequestDao.getTypes(),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_MAINTENANCE', 'ROLE_ADMIN', 'ROLE_LANDLORD')")
    @PostMapping("/maintenance-request/{maintenanceRequestId}/assign/{technicianId}")
    public ResponseEntity<?> assignTechnician(@PathVariable int maintenanceRequestId, @PathVariable int technicianId){
        jdbcMaintenanceRequestDao.assignTechnician(maintenanceRequestId,technicianId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ROLE_MAINTENANCE', 'ROLE_ADMIN', 'ROLE_LANDLORD')")
    @GetMapping("/maintenance-request/open")
    public ResponseEntity<?>getOpenMaintenanceRequests() {
        return new ResponseEntity<>(jdbcMaintenanceRequestDao.getOpenMaintenanceRequests(), HttpStatus.OK);
    }

}
