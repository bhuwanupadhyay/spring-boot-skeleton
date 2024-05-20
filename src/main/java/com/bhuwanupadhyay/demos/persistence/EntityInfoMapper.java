package com.bhuwanupadhyay.demos.persistence;

import com.bhuwanupadhyay.demos.model.EntityId;
import com.bhuwanupadhyay.demos.model.EntityInfo;
import com.bhuwanupadhyay.demos.model.EntityProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class EntityInfoMapper implements RowMapper<EntityInfo> {

    private final ObjectMapper objectMapper;

    public EntityInfoMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public EntityInfo mapRow(ResultSet rs, int i) throws SQLException {
        String id = rs.getString("id");
        String properties = rs.getString("properties");
        try {
            EntityId entityId = new EntityId(id);
            EntityProperties entityProperties = objectMapper.readValue(properties, EntityProperties.class);
            return new EntityInfo(entityId, entityProperties);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String toJson(EntityProperties properties) {
        try {
            return this.objectMapper.writeValueAsString(properties);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}