package com.stoom.address.util.model;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.stoom.address.util.model.AddressComponent;
import com.stoom.address.util.model.AddressComponentType;
import com.stoom.address.util.model.Geometry;
import com.stoom.address.util.model.Result;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodeResponse {

    private final List<Result> results;
    private final String status;
    @JsonProperty("error_message")
    private final String errorMessage;

    @JsonIgnore
    private int index = 0;

    public GeocodeResponse() {
        this(null, null, null);
    }

    public GeocodeResponse(List<Result> results, String status, String errorMessage) {
        this.results = results;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public List<Result> getResults() {
        return results;
    }

    public String getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public AddressComponent getAddressComponentByType(AddressComponentType t) {
        return getAddressComponentByType(index, t);
    }

    public AddressComponent getAddressComponentByType(int i, AddressComponentType t) {
        AddressComponent addressComponent;
        try {
            Optional<AddressComponent> optional = getResult(i).getAddressComponents().stream().filter(a -> a.getTypes().contains(t.getValue())).findFirst();
            addressComponent =  optional.isPresent() ? optional.get() : null;
        } catch(NullPointerException e) {
            addressComponent = null;
        }
        return addressComponent;
    }

    @JsonIgnore
    public String getSublocalityLongName() {
        return getSublocalityLongName(index);
    }

    public String getSublocalityLongName(int i) {
        AddressComponent addressComponent = getAddressComponentByType(i, AddressComponentType.SUBLOCALITY);
        return (addressComponent == null) ? null : addressComponent.getLongName();
    }

    @JsonIgnore
    public String getNeighborhoodLongName() {
        return getNeighborhoodLongName(index);
    }

    public String getNeighborhoodLongName(int i) {
        AddressComponent addressComponent = getAddressComponentByType(i, AddressComponentType.NEIGHBORHOOD);
        return (addressComponent == null) ? null : addressComponent.getLongName();
    }

    @JsonIgnore
    public String getLocalityLongName() {
        return getLocalityLongName(index);
    }

    public String getLocalityLongName(int i) {
        AddressComponent addressComponent = getAddressComponentByType(i, AddressComponentType.LOCALITY);
        return (addressComponent == null) ? null : addressComponent.getLongName();
    }

    @JsonIgnore
    public String getAdministrativeAreaLevel2LongName() {
        return getAdministrativeAreaLevel2LongName(index);
    }

    public String getAdministrativeAreaLevel2LongName(int i) {
        AddressComponent addressComponent = getAddressComponentByType(i, AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_2);
        return (addressComponent == null) ? null : addressComponent.getLongName();
    }

    @JsonIgnore
    public String getAdministrativeAreaLevel1LongName() {
        return getAdministrativeAreaLevel1LongName(index);
    }

    public String getAdministrativeAreaLevel1LongName(int i) {
        AddressComponent addressComponent = getAddressComponentByType(i, AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1);
        return (addressComponent == null) ? null : addressComponent.getLongName();
    }

    @JsonIgnore
    public String getCountryShortName() {
        return getCountryShortName(index);
    }

    public String getCountryShortName(int i) {
        AddressComponent addressComponent = getAddressComponentByType(i, AddressComponentType.COUNTRY);
        return (addressComponent == null) ? null : addressComponent.getShortName();
    }

    @JsonIgnore
    public String getFormattedAddress() {
        return getFormattedAddress(index);
    }

    public String getFormattedAddress(int i) {
        String formattedAddress;
        try {
            formattedAddress = getResult(i).getFormattedAddress();
        } catch(NullPointerException e) {
            formattedAddress = null;
        }
        return formattedAddress;
    }

    @JsonIgnore
    public Geometry getGeometry() {
        return getGeometry(index);
    }

    public Geometry getGeometry(int i) {
        Geometry geometry;
        try {
            geometry = getResult(i).getGeometry();
        } catch(NullPointerException e) {
            geometry = null;
        }
        return geometry;
    }

    @JsonIgnore
    public boolean isStatusOK() {
        return GeocodeResponseStatus.OK.getValue().equals(getStatus());
    }

    public Result getResult(int i) {
        Result result;
        try {
            result = getResults().get(i);
        } catch(NullPointerException | IndexOutOfBoundsException e) {
            result = null;
        }
        return result;
    }

}