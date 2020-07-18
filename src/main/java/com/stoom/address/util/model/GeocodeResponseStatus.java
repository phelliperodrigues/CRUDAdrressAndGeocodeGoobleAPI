package com.stoom.address.util.model;

public enum GeocodeResponseStatus {

    OK("OK"),
    ZERO_RESULTS("ZERO_RESULTS"),
    OVER_QUERY_LIMIT("OVER_QUERY_LIMIT"),
    REQUEST_DENIED("REQUEST_DENIED"),
    INVALID_REQUEST("INVALID_REQUEST");

    private final String value;

    private GeocodeResponseStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

