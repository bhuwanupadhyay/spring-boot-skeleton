package com.bhuwanupadhyay.demos.model;

import java.util.Collection;

public record PageResults<T>(
        Collection<T> items, Integer pageNumber, Integer pageSize, Long totalItems) {
}
