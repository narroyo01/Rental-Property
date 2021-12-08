package com.techelevator.dao;

import com.techelevator.model.MaintenanceRequest;
import com.techelevator.model.Property;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JdbcMaintenanceRequestDao implements MaintenanceRequestDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcMaintenanceRequestDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void markAsComplete(int id) {
        System.out.println(id);
        String sql = "UPDATE maintenance_request SET is_complete = true WHERE maintenance_request_id = ?;";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<MaintenanceRequest> getByAssignedTechnicianId(int id) {
        List<MaintenanceRequest> maintenanceRequests = new ArrayList<>();
        String sql = "SELECT m.maintenance_request_id, m.property_id, m.type_id, m.technician_id, m.requester_id, m.time_stamp, m.is_complete, m.email, m.phone, m.name, m.comments, p.address, t.description FROM maintenance_request m " +
                "JOIN maintenance_type t ON m.type_id = t.maintenance_type_id " +
                "JOIN property p ON p.property_id = m.property_id " +
                "WHERE technician_id = ? AND is_complete = false ORDER BY m.time_stamp;";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql,id);
        while (sqlRowSet.next()) {
            maintenanceRequests.add(mapRowToMaintenanceRequest(sqlRowSet));
        }
        return maintenanceRequests;
    }

    private MaintenanceRequest mapRowToMaintenanceRequest(SqlRowSet sqlRowSet) {
        MaintenanceRequest maintenanceRequest = new MaintenanceRequest();
        maintenanceRequest.setMaintenanceRequestId(sqlRowSet.getInt("maintenance_request_id"));
        maintenanceRequest.setPropertyId(sqlRowSet.getInt("property_id"));
        maintenanceRequest.setTypeId(sqlRowSet.getInt("type_id"));
        maintenanceRequest.setTechnicianId(sqlRowSet.getInt("technician_id"));
        maintenanceRequest.setRequesterId(sqlRowSet.getInt("requester_id"));
        maintenanceRequest.setTimeStamp(sqlRowSet.getTimestamp("time_stamp"));
        maintenanceRequest.setComplete(sqlRowSet.getBoolean("is_complete"));
        maintenanceRequest.setEmail(sqlRowSet.getString("email"));
        maintenanceRequest.setPhone(sqlRowSet.getString("phone"));
        maintenanceRequest.setName(sqlRowSet.getString("name"));
        maintenanceRequest.setComments(sqlRowSet.getString("comments"));
        maintenanceRequest.setAddress(sqlRowSet.getString("address"));
        maintenanceRequest.setDescription(sqlRowSet.getString("description"));
        return maintenanceRequest;
    }
}



