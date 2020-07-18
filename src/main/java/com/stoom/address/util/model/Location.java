package com.stoom.address.util.model;

import java.math.BigDecimal;

public class Location {

    private final BigDecimal lat;
    private final BigDecimal lng;

    public Location() {
        this(null, null);
    }

    public Location(BigDecimal lat, BigDecimal lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat() {
        return lat.toString();
    }

    public String getLng() {
        return lng.toString();
    }

}