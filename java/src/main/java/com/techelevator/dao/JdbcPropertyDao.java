package com.techelevator.dao;

import com.techelevator.model.Property;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JdbcPropertyDao implements  PropertyDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcPropertyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addProperty(Property property) {
        String sql = "INSERT INTO property (address, rent, is_available, tenant_id, image_url, description) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING property_id;";
        return jdbcTemplate.queryForObject(sql, int.class, property.getAddress(), property.getRent(), true, null, property.getImageUrl(), property.getDescription());
    }

    @Override
    public void updateProperty(int propertyId, Property property) {
        String sql = "UPDATE property" +
                " SET address = ?, rent = ?, is_available = ?, image_url = ?, description = ? " +
                " WHERE property_id = ?";
        jdbcTemplate.update(sql, property.getAddress(), property.getRent(), property.getAvailable(),  property.getImageUrl(), property.getDescription(), propertyId);
    }

    @Override
    public Property getPropertyById(int id) {
        String sql = "SELECT property_id, address, rent, is_available, tenant_id, image_url, username, description" +
                " FROM property" +
                " LEFT JOIN users on tenant_id = user_id " +
                "WHERE property_id = ?";
        SqlRowSet rowset = jdbcTemplate.queryForRowSet(sql, id);
        Property property = new Property();
        if(rowset.next()) {
            property = mapRowToProperty(rowset);
        }

        return property;
    }

    @Override
    public List<Property> getAvailableProperties() {
        List<Property> properties = new ArrayList<>();
        String sql = "SELECT property_id, address, rent, is_available, tenant_id, image_url, null as username, description " +
                "FROM property " +
                "WHERE is_available = true";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        while(sqlRowSet.next()) {
            properties.add(mapRowToProperty(sqlRowSet));
        }
        return properties;
    }

    @Override
    public List<Property> getProperties() {
        List<Property> properties = new ArrayList<>();
        String sql = "SELECT property_id, address, rent, is_available, tenant_id, image_url, null as username, description " +
                "FROM property ";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        while(sqlRowSet.next()) {
            properties.add(mapRowToProperty(sqlRowSet));
        }
        return properties;
    }

    @Override
    public void assignTenant(int propertyId, int tenantId) {
        String sql = "UPDATE property SET tenant_id = ? WHERE property_id = ?;";
        jdbcTemplate.update(sql, tenantId, propertyId);
    }

    private Property mapRowToProperty(SqlRowSet rowSet) {
        Property property = new Property();
        property.setAddress(rowSet.getString("address"));
        property.setRent(rowSet.getInt("rent"));
        property.setAvailable(rowSet.getBoolean("is_available"));
        property.setTenantId(rowSet.getInt("tenant_id"));
        property.setImageUrl(rowSet.getString("image_url"));
        property.setUsername(rowSet.getString("username"));
        property.setDescription(rowSet.getString("description"));
        property.setPropertyId(rowSet.getInt("property_id"));
        return property;
    }
}
