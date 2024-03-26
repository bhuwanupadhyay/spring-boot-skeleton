package com.bhuwanupadhyay.demos.core.web;

import com.bhuwanupadhyay.demos.core.model.*;

import java.util.UUID;
import org.springframework.stereotype.Service;

@SuppressWarnings("preview")
@Service
public class DefaultService implements IService {

    private final IEntityRepository repository;

    public DefaultService(IEntityRepository repository) {
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
