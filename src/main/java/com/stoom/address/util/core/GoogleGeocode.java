package com.stoom.address.util.core;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.stoom.address.util.model.GeocodeResponse;

public class GoogleGeocode {

    private String languageCode = "pt-BR";
    private String apiKey;
    private String address;
    private String lat;
    private String lng;
    private boolean reverse;

    public GoogleGeocode(String apiKey, String address) {
        this.apiKey = apiKey;
        this.address = address;
    }

    public GoogleGeocode(String apiKey, String lat, String lng) {
        this.apiKey = apiKey;
        this.lat = lat;
        this.lng = lng;
        reverse = true;
    }


    public String getJsonString() throws IOException {
        URL url = new URL(getHttpUrl());
        URLConnection conn = url.openConnection();
        return getJsonString(conn.getInputStream());
    }

    private String getHttpUrl() throws IOException {
        StringBuilder httpUrl = new StringBuilder("https://maps.googleapis.com/maps/api/geocode/json?sensor=false&");
        if (languageCode != null && !languageCode.isEmpty()) {
            httpUrl.append("language=").append(languageCode).append("&");
        }
        httpUrl.append("key=").append(apiKey).append("&");
        if (reverse) {
            httpUrl.append("latlng=").append(lat).append(",").append(lng);
        } else {
            httpUrl.append("address=").append(URLEncoder.encode(address, "utf-8"));
        }
        return httpUrl.toString();
    }

    private String getJsonString(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            out.append(line);
        }
        return out.toString();
    }

    public GeocodeResponse getResponseObject() throws IOException {
        String json = getJsonString();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, GeocodeResponse.class);
    }

}