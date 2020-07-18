package com.stoom.address.util.service;

import com.stoom.address.model.Address;
import com.stoom.address.util.core.GoogleGeocode;
import com.stoom.address.util.model.GeocodeResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static java.lang.String.format;

@Service
public class GeoService {

    private static final String API_KEY = "AIzaSyDTK0igIQTCi5EYKL9tzOIJ9N6FUASGZos";


    public Address getLocalization(Address address)  {

        try {
            String addressJson = formatAddress(address);
            GoogleGeocode googleGeocode = new GoogleGeocode(API_KEY,addressJson);


            GeocodeResponse geocodeResponse = googleGeocode.getResponseObject();
            address.latitude(geocodeResponse.getGeometry().getLocation().getLat());
            address.longitude(geocodeResponse.getGeometry().getLocation().getLng());
        } catch (IOException e) {

        }

        return address;
    }


    private String formatAddress(Address address) {
        return format(
                "%s, %s. %s - %s, %s. %s",
                address.getStreetName(),
                address.getNumber(),
                address.getCity(),
                address.getState(),
                address.getCountry(),
                address.getZipcode()).replaceAll(" ", "+");
    }
}
