package com.bhuwanupadhyay.demos.model;

import java.util.Collection;

public record PageResults<T>(
    Collection<T> items, int pageNumber, int pageSize, int totalItems, int totalPages) {}