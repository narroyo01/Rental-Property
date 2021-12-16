package com.techelevator.controller;

import com.techelevator.dao.JdbcUserDao;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    JdbcUserDao jdbcUserDao;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LANDLORD', 'ROLE_MAINTENANCE')")
    @GetMapping("/user")
    public ResponseEntity<?> get(@RequestParam(name = "role") String role) {
        return new ResponseEntity<>(jdbcUserDao.getUsersByRole(role), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LANDLORD')")
    @PutMapping("/user")
    public ResponseEntity<?> update(@RequestBody User user) {
        jdbcUserDao.updateUser(user.getId(), user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
