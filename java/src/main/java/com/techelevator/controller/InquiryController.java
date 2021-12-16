package com.techelevator.controller;

import com.techelevator.dao.JdbcInquiryDao;
import com.techelevator.model.Inquiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class InquiryController {

    @Autowired
    JdbcInquiryDao jdbcInquiryDao;

    @PostMapping("/inquiry")
    public ResponseEntity<?> createInquiry(@RequestBody Inquiry inquiry){
        jdbcInquiryDao.createInquiry(inquiry);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
