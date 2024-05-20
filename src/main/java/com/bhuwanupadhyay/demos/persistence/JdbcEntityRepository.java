package com.bhuwanupadhyay.demos.persistence;

import com.bhuwanupadhyay.demos.model.EntityId;
import com.bhuwanupadhyay.demos.model.EntityInfo;
import com.bhuwanupadhyay.demos.model.EntityProperties;
import com.bhuwanupadhyay.demos.model.IEntityRepository;
import com.bhuwanupadhyay.demos.model.PageResults;
import com.bhuwanupadhyay.demos.model.Query;
import com.bhuwanupadhyay.demos.model.SearchQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcEntityRepository implements IEntityRepository {

    private final JdbcTemplate jdbc;

    private final EntityInfoMapper mapper;

    public JdbcEntityRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.mapper = new EntityInfoMapper(new ObjectMapper());
    }

    @Override
    public Optional<EntityInfo> createAndGet(EntityId entityId, EntityProperties properties) {
        String SQL_INSERT = "insert into entities(id, properties) values(?,?)";
        jdbc.update(SQL_INSERT, entityId.id(), mapper.toJson(properties));
        return getById(entityId);
    }

    @Override
    public Optional<EntityInfo> updateAndGet(EntityId entityId, EntityProperties properties) {
        String SQL_UPDATE = "update entities set properties = ? where id = ?";
        jdbc.update(SQL_UPDATE, entityId.id(), mapper.toJson(properties));
        return getById(entityId);
    }

    @Override
    public void deleteById(EntityId entityId) {
        String SQL_DELETE = "delete from entities where id = ?";
        jdbc.update(SQL_DELETE, entityId.id());
    }

    @Override
    public Optional<EntityInfo> getById(EntityId entityId) {
        String SQL_FIND = "select * from entities where id = ?";
        EntityInfo entityInfo = jdbc.queryForObject(SQL_FIND, mapper, entityId.id());
        return Optional.ofNullable(entityInfo);
    }

    @Override
    public Collection<EntityInfo> get(Query query) {
        String SQL_GET_ALL = "select * from entities";
        return jdbc.query(SQL_GET_ALL, mapper);
    }

    @Override
    public PageResults<EntityInfo> search(SearchQuery query) {
        Integer pageSize = Optional.ofNullable(query.pageSize()).orElse(50);
        Integer pageNumber = Optional.ofNullable(query.pageNumber()).orElse(1);
        int offset = (pageNumber - 1) * pageSize;

        // SQL query with LIMIT and OFFSET
        String SQL_GET_PAGE = "SELECT * FROM entities ORDER BY id LIMIT ? OFFSET ?";
        String SQL_GET_TOTAL_ITEMS = "SELECT COUNT(*) FROM entities";

        // Query to get paginated results
        List<EntityInfo> entityInfos = jdbc.query(SQL_GET_PAGE, mapper, pageSize, offset);

        // Query to get total number of items
        Long totalItems = jdbc.queryForObject(SQL_GET_TOTAL_ITEMS, Long.class);
        return new PageResults<>(entityInfos, pageNumber, pageSize, totalItems);
    }

}
