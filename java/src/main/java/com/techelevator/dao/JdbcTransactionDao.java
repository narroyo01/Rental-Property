package com.techelevator.dao;

import com.techelevator.model.Ledger;
import com.techelevator.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class JdbcTransactionDao implements TransactionDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTransactionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transaction> viewRentStatus() {
        List<Transaction> allRent = new ArrayList<>();
        String sql = "SELECT address, transaction_id, transactions.property_id, time_initiated, amount_due, amount_paid, time_paid FROM transactions " +
                " JOIN property ON property.property_id = transactions.property_id;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        while(rowSet.next()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Transaction transaction = new Transaction();
            transaction.setPropertyId(rowSet.getInt("property_id"));
            transaction.setTimeInitiated(rowSet.getTimestamp("time_initiated"));
            transaction.setAmountDue(rowSet.getInt("amount_due"));
            transaction.setAmountPaid(rowSet.getInt("amount_paid"));
            transaction.setTimePaid(rowSet.getString("time_paid"));
            transaction.setTransactionId(rowSet.getInt("transaction_id"));
            transaction.setAddress(rowSet.getString("address"));
            LocalDate timeDue = transaction.getTimeInitiated().toLocalDateTime().toLocalDate();
            timeDue = timeDue.plusMonths(1);
            Timestamp newTimeDue = Timestamp.valueOf(timeDue.atStartOfDay());
            //format date
            transaction.setTimeDue(formatter.format(newTimeDue));
            String status = "";
            if (transaction.getAmountPaid() >= transaction.getAmountDue()) {
                status = "paid";
            } else if (transaction.getAmountPaid() < transaction.getAmountDue() && (transaction.compareTime(transaction.getTimePaid(), transaction.getTimeDue()) <= 0)) {
                status = "due";
            } else if (transaction.getAmountPaid() < transaction.getAmountDue() && (transaction.compareTime(transaction.getTimePaid(), transaction.getTimeDue())) > 0){
                status = "overdue";
            }
            transaction.setStatus(status);
            allRent.add(transaction);
        }
        return allRent;
    }

    @Override
    public Transaction getTransactionById(int id) {
        String sql = "SELECT transaction_id, time_initiated, time_paid, amount_due, amount_paid, property_id, tenant_id" +
                " FROM transactions" +
                " LEFT JOIN users on tenant_id = user_id " +
                "WHERE tenant_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        Transaction transaction = new Transaction();
        if(rowSet.next()) {
            transaction = mapTransactionToRowSet(rowSet);
        }
        return transaction;
    }

    @Override
    public List<Ledger> getLedgersByTenantId(int tenantId) {
        String sql = "SELECT DISTINCT property_id" +
                " FROM transactions" +
                " WHERE tenant_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, tenantId);
        List<Integer> propertyIds = new ArrayList<>();
        while(rowSet.next()) {
            propertyIds.add(rowSet.getInt("property_id"));
        }

        List<Ledger> ledgers = new ArrayList<>();

        for(int propertyId : propertyIds) {
            ledgers.add(getLedgerByPropertyIdAndTenantId(propertyId, tenantId));
        }
        return ledgers;
    }

    @Override
    public Ledger getLedgerByPropertyIdAndTenantId(int propertyId, int tenantId) {
        String sql = "SELECT transaction_id, time_initiated, time_paid, amount_due, amount_paid, p.property_id, t.tenant_id, address" +
                " FROM transactions t" +
                " JOIN property p on p.property_id = t.property_id" +
                " WHERE p.property_id = ? AND t.tenant_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, propertyId, tenantId);
        Ledger ledger = new Ledger();
        while (rowSet.next()) {
            Transaction transaction = mapTransactionToRowSet(rowSet);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            LocalDate timeDue = transaction.getTimeInitiated().toLocalDateTime().toLocalDate();
            timeDue = timeDue.plusMonths(1);
            Timestamp newTimeDue = Timestamp.valueOf(timeDue.atStartOfDay());
            //format date
            transaction.setTimeDue(formatter.format(newTimeDue));
            ledger.addTransaction(transaction);
            ledger.setAddress(rowSet.getString("address"));
        }
        return ledger;
    }

    @Override
    public void payRentByTransactionId(int transactionId, int amountPaid) {
        System.out.println(amountPaid);
        String sql = "UPDATE transactions " +
                "SET amount_paid = amount_paid + ?, time_paid = CURRENT_DATE " +
                "WHERE transaction_id = ?";
        jdbcTemplate.update(sql, amountPaid, transactionId);
    }

    private Transaction mapTransactionToRowSet(SqlRowSet rowSet){
        Transaction transaction = new Transaction();
        transaction.setTransactionId(rowSet.getInt("transaction_id"));
        transaction.setTimeInitiated(rowSet.getTimestamp("time_initiated"));
        transaction.setTimePaid(rowSet.getString("time_paid"));
        transaction.setAmountDue(rowSet.getInt("amount_due"));
        transaction.setAmountPaid(rowSet.getInt("amount_paid"));
        transaction.setPropertyId(rowSet.getInt("property_id"));
        transaction.setTenantId(rowSet.getInt("tenant_id"));
        return transaction;
    }
}
