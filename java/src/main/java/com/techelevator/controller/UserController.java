package com.techelevator.controller;

import com.techelevator.dao.JdbcUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    JdbcUserDao jdbcUserDao;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LANDLORD')")
    @GetMapping("/user")
    public ResponseEntity<?> get(@RequestParam(name = "role") String role) {
        return new ResponseEntity<>(jdbcUserDao.getUsersByRole(role), HttpStatus.OK);
    }
}
