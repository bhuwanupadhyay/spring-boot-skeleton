package com.bhuwanupadhyay.demos.model;

import java.util.Objects;

public record EntityId(String id) {

  public EntityId {
    Objects.requireNonNull(id, "Entity id is null");
  }
}
