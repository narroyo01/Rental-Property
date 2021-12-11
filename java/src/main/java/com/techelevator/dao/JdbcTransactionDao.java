package com.techelevator.dao;

import com.techelevator.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JdbcTransactionDao implements  TransactionDao{

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
            Transaction transaction = new Transaction();
            transaction.setPropertyId(rowSet.getInt("property_id"));
            transaction.setTimeInitiated(rowSet.getTimestamp("time_initiated"));
            transaction.setAmountDue(rowSet.getInt("amount_due"));
            transaction.setAmountPaid(rowSet.getInt("amount_paid"));
            transaction.setTimePaid(rowSet.getTimestamp("time_paid"));
            transaction.setTransactionId(rowSet.getInt("transaction_id"));
            transaction.setAddress(rowSet.getString("address"));
            LocalDate timeDue = transaction.getTimeInitiated().toLocalDateTime().toLocalDate();
            timeDue = timeDue.plusMonths(1);
            Timestamp newTimeDue = Timestamp.valueOf(timeDue.atStartOfDay());
            //format date
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            //transaction.setTimeDue(formatter.format(newTimeDue));
            String status = "";
            if (transaction.getAmountPaid() >= transaction.getAmountDue()) {
                status = "paid";
            } else if (transaction.getAmountPaid() < transaction.getAmountDue() && transaction.getTimePaid().before(transaction.getTimeDue())){
                status = "due";
            } else if (transaction.getAmountPaid() < transaction.getAmountDue() && transaction.getTimePaid().after(transaction.getTimeDue())){
                status = "overdue";
            }
            transaction.setStatus(status);
            allRent.add(transaction);
        }
        return allRent;
    }

    private Transaction mapTransactionToRowSet(SqlRowSet rowSet){
        Transaction transaction = new Transaction();
        transaction.setTransactionId(rowSet.getInt("transaction_id"));
        transaction.setTimeInitiated(rowSet.getTimestamp("time_initiated"));
        transaction.setTimePaid(rowSet.getTimestamp("time_paid"));
        transaction.setAmountDue(rowSet.getInt("amount_due"));
        transaction.setAmountPaid(rowSet.getInt("amount_paid"));
        transaction.setPropertyId(rowSet.getInt("property_id"));
        transaction.setTenantId(rowSet.getInt("tenant_id"));
        return transaction;
    }
}
