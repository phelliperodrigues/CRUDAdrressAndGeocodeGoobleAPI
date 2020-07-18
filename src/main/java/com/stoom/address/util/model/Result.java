package com.stoom.address.util.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

    @JsonProperty("address_components")
    private final List<AddressComponent> addressComponents;
    @JsonProperty("formatted_address")
    private final String formattedAddress;
    private final Geometry geometry;
    private final List<String> types;

    public Result() {
        this(null, null, null, null);
    }

    public Result(List<AddressComponent> addressComponents, String formattedAddress, Geometry geometry, List<String> types) {
        this.addressComponents = addressComponents;
        this.formattedAddress = formattedAddress;
        this.geometry = geometry;
        this.types = types;
    }

    public List<AddressComponent> getAddressComponents() {
        return addressComponents;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public List<String> getTypes() {
        return types;
    }

}