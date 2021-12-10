package com.techelevator.model;

import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private Timestamp timeInitiated;
    private Timestamp timePaid;
    private int amountDue;
    private int amountPaid;
    private int propertyId;
    private int tenantId;
    private Timestamp timeDue;
    private String status;

    public Transaction(Timestamp timeDue, String status, int transactionId, Timestamp timeInitiated, Timestamp timePaid, int amountDue, int amountPaid, int propertyId, int tenantId) {
        this.transactionId = transactionId;
        this.timeInitiated = timeInitiated;
        this.timePaid = timePaid;
        this.amountDue = amountDue;
        this.amountPaid = amountPaid;
        this.propertyId = propertyId;
        this.tenantId = tenantId;
        this.timeDue = timeDue;
        this.status = status;
    }

    public Transaction(){

    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Timestamp getTimeInitiated() {
        return timeInitiated;
    }

    public void setTimeInitiated(Timestamp timeInitiated) {
        this.timeInitiated = timeInitiated;
    }

    public Timestamp getTimePaid() {
        return timePaid;
    }

    public void setTimePaid(Timestamp timePaid) {
        this.timePaid = timePaid;
    }

    public int getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(int amountDue) {
        this.amountDue = amountDue;
    }

    public int getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(int amountPaid) {
        this.amountPaid = amountPaid;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public Timestamp getTimeDue() {
        return timeDue;
    }

    public void setTimeDue(Timestamp timeDue) {
        this.timeDue = timeDue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
