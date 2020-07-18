package com.stoom.address.util.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Geometry {

    private final Bounds bounds;
    private final Location location;
    @JsonProperty("location_type")
    private final String locationType;
    private final Bounds viewport;

    public Geometry() {
        this(null, null, null, null);
    }

    public Geometry(Bounds bounds, Location location, String locationType, Bounds viewport) {
        this.bounds = bounds;
        this.location = location;
        this.locationType = locationType;
        this.viewport = viewport;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public Location getLocation() {
        return location;
    }

    public String getLocationType() {
        return locationType;
    }

    public Bounds getViewport() {
        return viewport;
    }

}