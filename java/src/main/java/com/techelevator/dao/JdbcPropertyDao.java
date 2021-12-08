package com.techelevator.dao;

import com.techelevator.model.Property;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JdbcPropertyDao implements  PropertyDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcPropertyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addProperty(Property property) {
        String sql = "INSERT INTO property (address, rent, is_available, tenant_id, image_url) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING property_id;";
        return jdbcTemplate.queryForObject(sql, int.class, property.getAddress(), property.getRent(), true, null, property.getImageUrl());
    }

    @Override
    public void updateProperty(int propertyId, Property property) {
        String sql = "UPDATE property" +
                " SET address = ?, rent = ?, is_available = ?, tenant_id = ?, image_url = ?" +
                " WHERE property_id = ?";
        jdbcTemplate.update(sql, property.getAddress(), property.getRent(), property.getAvailable(), property.getTenantId(), property.getImageUrl(), propertyId);
    }
}
