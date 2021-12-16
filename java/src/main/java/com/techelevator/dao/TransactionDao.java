package com.techelevator.dao;

import com.techelevator.model.Ledger;
import com.techelevator.model.Transaction;

import java.util.List;

public interface TransactionDao {

    List<Transaction> viewRentStatus();

    Transaction getTransactionById(int id);

    List<Ledger> getLedgersByTenantId(int tenantId);

    Ledger getLedgerByPropertyIdAndTenantId(int propertyId, int tenantId);

    void payRentByTransactionId(int transactionId, int amountPaid);

}
