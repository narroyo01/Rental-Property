package com.techelevator.controller;

import com.techelevator.dao.JdbcInquiryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class InquiryController {

    @Autowired
    JdbcInquiryDao jdbcInquiryDao;

    @PostMapping("/inquiry")
    public ResponseEntity<?> createInquiry(@RequestBody Integer propertyId){
        jdbcInquiryDao.createInquiry(propertyId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
