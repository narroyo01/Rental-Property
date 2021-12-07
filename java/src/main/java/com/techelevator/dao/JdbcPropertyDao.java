package com.techelevator.dao;

import com.techelevator.model.Property;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

@Service
public class JdbcPropertyDao implements  PropertyDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcPropertyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addProperty(String address, int rent, boolean isAvailable, int tenantId) {
        String sql = "INSERT INTO property (address, rent, is_available, tenant_id) " +
                "VALUES (?, ?, ?, ?);";
        jdbcTemplate.queryForObject(sql, int.class, address, rent, isAvailable, tenantId);
    }
}
