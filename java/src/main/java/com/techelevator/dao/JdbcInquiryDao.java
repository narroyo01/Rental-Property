package com.techelevator.dao;

import com.techelevator.model.Inquiry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
@Service
public class JdbcInquiryDao implements  InquiryDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcInquiryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createInquiry(Inquiry inquiry) {
        String sql = "INSERT INTO inquiry (name, email, phone, message, property_id)" +
                "VALUES ( ?, ?, ?, ?, ?) ;";
        jdbcTemplate.update(sql,inquiry.getName(), inquiry.getEmail(), inquiry.getPhone(), inquiry.getMessage(), inquiry.getPropertyId());
    }
}
