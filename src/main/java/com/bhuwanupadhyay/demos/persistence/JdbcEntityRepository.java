package com.bhuwanupadhyay.demos.persistence;

import com.bhuwanupadhyay.demos.model.EntityId;
import com.bhuwanupadhyay.demos.model.EntityInfo;
import com.bhuwanupadhyay.demos.model.EntityProperties;
import com.bhuwanupadhyay.demos.model.IEntityRepository;
import com.bhuwanupadhyay.demos.model.PageResults;
import com.bhuwanupadhyay.demos.model.Query;
import com.bhuwanupadhyay.demos.model.SearchQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcEntityRepository implements IEntityRepository {

    @Override
    public Optional<EntityProperties> createAndGet(EntityId entityId, EntityProperties properties) {
        return Optional.empty();
    }

    @Override
    public Optional<EntityProperties> updateAndGet(EntityId entityId, EntityProperties properties) {
        return Optional.empty();
    }

    @Override
    public void deleteById(EntityId entityId) {
    }

    @Override
    public void delete(Query query) {
    }

    @Override
    public Optional<EntityInfo> getById(EntityId entityId) {
        return Optional.empty();
    }

    @Override
    public Collection<EntityInfo> get(Query query) {
        return new ArrayList<>();
    }

    @Override
    public PageResults<EntityInfo> search(SearchQuery query) {
        Collection<EntityInfo> entityInfos = new ArrayList<>();
        return new PageResults<>(entityInfos, 0, 10, 100, 100);
    }
}
