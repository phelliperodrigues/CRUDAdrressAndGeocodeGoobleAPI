package com.stoom.address.util.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressComponent {

    @JsonProperty("long_name")
    private final String longName;
    @JsonProperty("short_name")
    private final String shortName;
    private final List<String> types;

    public AddressComponent() {
        this(null, null, null);
    }

    public AddressComponent(String longName, String shortName, List<String> types) {
        this.longName = longName;
        this.shortName = shortName;
        this.types = types;
    }

    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }

    public List<String> getTypes() {
        return types;
    }

}