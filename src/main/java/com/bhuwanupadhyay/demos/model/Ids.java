package com.bhuwanupadhyay.demos.model;

public record Ids(String[] ids) {
    public static Ids of(String id) {
        return new Ids(new String[]{id});
    }
}
