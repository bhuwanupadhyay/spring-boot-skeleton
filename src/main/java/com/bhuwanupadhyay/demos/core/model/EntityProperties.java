package com.bhuwanupadhyay.demos.core.model;

import java.util.Map;
import java.util.Objects;

public record EntityProperties(Map<String, Property> data) {

  public EntityProperties {
    Objects.requireNonNull(data, "Entity properties data is null");
  }

  public int size() {
    return data.size();
  }
}
