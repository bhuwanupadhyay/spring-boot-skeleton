package com.bhuwanupadhyay.demos.model;

import java.util.Objects;

public record EntityInfo(EntityId entityId, EntityProperties properties) {

  public EntityInfo {
    Objects.requireNonNull(entityId, "EntityInfo entityId is null");
    Objects.requireNonNull(properties, "EntityInfo properties is null");
  }
}
