package com.techelevator.dao;

import com.techelevator.model.Inquiry;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcInquiryDao implements  InquiryDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcInquiryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createInquiry(Integer propertyId) {
        String sql = "INSERT INTO inquiry (name, email, phone, message)" +
                "VALUES ( ?, ?, ?, ?) ;";
        jdbcTemplate.update(sql,propertyId);
    }
}
