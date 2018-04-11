package com.sadi.sreda.model;

/**
 * Created by Sadi on 4/11/2018.
 */

public class LocationInfo {
    private String location_name,latitude,longitude,radius,radius_length;

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getRadius_length() {
        return radius_length;
    }

    public void setRadius_length(String radius_length) {
        this.radius_length = radius_length;
    }
}
