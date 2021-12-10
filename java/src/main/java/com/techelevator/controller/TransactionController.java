package com.techelevator.controller;

import com.techelevator.dao.TransactionDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    private TransactionDao transactionDao;

    public TransactionController(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LANDLORD')")
    @GetMapping("/transaction")
    public ResponseEntity<?> getAllRent() {
        return new ResponseEntity<>(transactionDao.viewRentStatus(), HttpStatus.OK);
    }

}
