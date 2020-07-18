package com.stoom.address.util.model;

public class Bounds {

    private final Location northeast;
    private final Location southwest;

    public Bounds() {
        this(null, null);
    }

    public Bounds(Location northeast, Location southwest) {
        this.northeast = northeast;
        this.southwest = southwest;
    }

    public Location getNortheast() {
        return northeast;
    }

    public Location getSouthwest() {
        return southwest;
    }

}