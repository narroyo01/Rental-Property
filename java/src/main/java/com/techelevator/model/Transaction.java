package com.techelevator.model;

import java.sql.Timestamp;

import static java.lang.Integer.parseInt;

public class Transaction {
    private int transactionId;
    private Timestamp timeInitiated;
    private String timePaid;
    private int amountDue;
    private int amountPaid;
    private int propertyId;
    private int tenantId;
    private String timeDue;
    private String status;
    private String address;

    public Transaction(String address, String timeDue, String status, int transactionId, Timestamp timeInitiated, String timePaid, int amountDue, int amountPaid, int propertyId, int tenantId) {
        this.transactionId = transactionId;
        this.timeInitiated = timeInitiated;
        this.timePaid = timePaid;
        this.amountDue = amountDue;
        this.amountPaid = amountPaid;
        this.propertyId = propertyId;
        this.tenantId = tenantId;
        this.timeDue = timeDue;
        this.status = status;
        this.address = address;
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

    public String getTimePaid() {
        return timePaid;
    }

    public void setTimePaid(String timePaid) {
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

    public String getTimeDue() {
        return timeDue;
    }

    public void setTimeDue(String timeDue) {
        this.timeDue = timeDue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int compareTime(String timeOne, String timeTwo) {
        int monthOne = parseInt(timeOne.substring(5, 7));
        int monthTwo = parseInt(timeTwo.substring(5, 7));

        int dayOne = parseInt(timeOne.substring(8));
        int dayTwo = parseInt(timeTwo.substring(8));

        int yearOne = parseInt(timeOne.substring(0, 4));
        int yearTwo = parseInt(timeTwo.substring(0, 4));

        if (yearOne > yearTwo) {
            return 1;
        } else if (yearOne < yearTwo) {
            return -1;
        } else if (yearOne == yearTwo) {
            if (monthOne > monthTwo) {
                return 1;
            } else if (monthOne < monthTwo) {
                return -1;
            } else if (monthOne == monthTwo) {
                if (dayOne > dayTwo) {
                    return 1;
                } else if (dayOne < dayTwo) {
                    return -1;
                } else if (dayOne == dayTwo) {
                    return 0;
                }
            }
        }
        return -2;
    }
}
