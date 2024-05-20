package com.bhuwanupadhyay.demos.application;

import com.bhuwanupadhyay.demos.model.*;

import java.util.UUID;
import org.springframework.stereotype.Service;

@SuppressWarnings("preview")
@Service
public class EntityService {

    private final IEntityRepository repository;

    public EntityService(IEntityRepository repository) {
        this.repository = repository;
    }

    public EntityId create(EntityProperties properties) {
        String newId = UUID.randomUUID().toString();
        EntityId entityId = new EntityId(newId);
        repository.create(entityId, properties);
        return entityId;
    }

    public void update(EntityId entityId, EntityProperties properties) {
        repository.update(entityId, properties);
    }

    public EntityInfo getById(EntityId entityId) {
        return repository.getById(entityId)
                .orElseThrow(() -> new NotFoundException(STR."Not found: \{entityId}"));
    }

    public void deleteById(EntityId entityId) {
        repository.deleteById(entityId);
    }

    public PageResults<EntityInfo> search(SearchQuery query) {
        return repository.search(query);
    }
}
