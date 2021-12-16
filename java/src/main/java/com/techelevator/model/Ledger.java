package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

public class Ledger {

    private List<Transaction> ledger = new ArrayList<>();
    private String address;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addTransaction(Transaction transaction) {
        this.ledger.add(transaction);
    }

    public List<Transaction> getLedger() {
        return ledger;
    }

    public void setLedger(List<Transaction> ledger) {
        this.ledger = ledger;
    }

    public Ledger() {
    }

    public Ledger(List<Transaction> ledger, String address) {
        this.ledger = ledger;
        this.address = address;
    }
}
