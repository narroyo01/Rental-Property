package com.techelevator.controller;

import com.techelevator.dao.TransactionDao;
import com.techelevator.model.PaymentAmount;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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

    @GetMapping("/transaction/{tenantId}")
    public ResponseEntity<?> get(@PathVariable int tenantId) {
        return new ResponseEntity<>(transactionDao.getLedgersByTenantId(tenantId), HttpStatus.OK);
    }
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/transaction/{transactionId}")
    public ResponseEntity<?> put(@PathVariable int transactionId, @RequestBody PaymentAmount paymentAmount) {
        System.out.println(paymentAmount.getPaymentAmount());
        transactionDao.payRentByTransactionId(transactionId, paymentAmount.getPaymentAmount());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
